package com.controller;

import com.extra.GoogleVerifier;
import com.extra.PayPalClient;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.model.Candidacy;
import com.model.Part;
import com.model.Project;
import com.service.CandidacyService;
import com.service.PartService;
import com.service.ProjectService;
import com.service.UserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hibernate.exception.ConstraintViolationException;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProjectController {

    private ProjectService projectService;
    private PartService partService;
    private CandidacyService candidacyService;
    private UserService userService;
    private final String key = "SnaporazKey";
    private final String[] genres = {"Animazione", "Avventura", "Biografico", "Commedia", "Documentario", "Drammatico", "Erotico", "Fantascienza", "Fantasy/Fantastico", "Guerra", "Horror", "Musical", "Storico", "Thriller", "Western"};
    private final String[] cast = {"Attore Protagonista", "Attrice Protagonista", "Attore non Protagonista", "Attrice non Protagonista", "Comparsa", "Doppiatore"};
    private final String[] troupe = {"Regista", "Aiuto Regista", "Assistente alla Regia", "Sceneggiatore", "Segretario di Edizione", "Direttore della Fotografia", "Operatore alla Macchina", "Assistente Operatore", "Capo Elettricista", "Elettricista", "Capo Macchinista", "Macchinista", "Costumista", "Capo Truccatore", "Truccatore", "Scenografo", "Fonico", "Microfonista", "Compositore", "Direttore del Montaggio", "Addetto al Montaggio"};

    @Autowired(required = true)
    @Qualifier(value = "projectService")
    public void setProjectService(ProjectService s) {
        this.projectService = s;
    }

    @Autowired(required = true)
    @Qualifier(value = "partService")
    public void setPartService(PartService s) {
        this.partService = s;
    }

    @Autowired(required = true)
    @Qualifier(value = "candidacyService")
    public void setCandidacyService(CandidacyService s) {
        this.candidacyService = s;
    }

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService s) {
        this.userService = s;
    }

    private void addPartProperties(Model model, Part part, String userId) {
        model.addAttribute("part", part);
        if (part.getUser() != null) {
            model.addAttribute("user", userService.getUserById(part.getUser()));
        } else if (projectService.getProjectById(part.getProject()).getOwner().equals(userId)) {
            List<Candidacy> candidacies = candidacyService.listCandidaciesByPart(part.getId());
            model.addAttribute("candidacies", candidacies);
            for (Candidacy c : candidacies) {
                model.addAttribute("cand" + c.getId(), userService.getUserById(c.getUser()));
            }
        }
    }

    /**
     * Returns three lists for the main page: the most funded, most recent
     * projects and the ones closest to their objectives
     *
     * @param model
     * @return "home" (most funded projects, most recent projects, projects
     * closest to their objectives)
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainPage(Model model) {
        model.addAttribute("mostFoundedProjects", projectService.listMostFoundedProjects());
        model.addAttribute("mostRecentProjects", projectService.listMostRecentProjects());
        model.addAttribute("closestProjects", projectService.listClosestProjects());
        return "home";
    }

    /**
     * Returns informations of the project with the given id and check if the
     * current user is its owner
     *
     * @param model
     * @param id
     * @param idTokenString
     * @return "project" (project informations, parts, candidacies and if
     * current user owns it) in case of success, "response" (error message)
     * otherwise
     */
    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public String getProjectById(Model model, @RequestParam int id, @RequestParam String idTokenString) {
        GoogleIdToken idToken = GoogleVerifier.verify(idTokenString);
        Project pr = projectService.getProjectById(id);
        if (pr != null) {
            model.addAttribute("project", pr);
            List<Part> parts = partService.listPartsByProject(id);
            model.addAttribute("parts", parts);
            boolean owner = idToken != null && idToken.getPayload().getSubject().equals(pr.getOwner());
            model.addAttribute("owner", owner);
            for (Part p : parts) {
                if (p.getUser() != null) {
                    model.addAttribute("user" + p.getUser(), userService.getUserById(p.getUser()));
                } else if (owner) {
                    List<Candidacy> candidacies = candidacyService.listCandidaciesByPart(p.getId());
                    model.addAttribute("part" + p.getId(), candidacies);
                    for (Candidacy c : candidacies) {
                        model.addAttribute("cand" + c.getId(), userService.getUserById(c.getUser()));
                    }
                }
            }
            model.addAttribute("days", Days.daysBetween(new DateTime(), new DateTime(pr.getDeadLine())).getDays());
            model.addAttribute("related", projectService.listRelatedProjects(pr));
        }
        return "project";
    }

    /**
     * Returns a list of all the accepted movie genres
     *
     * @param model
     * @return "list" (genres)
     */
    @RequestMapping(value = "/genres", method = RequestMethod.GET)
    public String getGenres(Model model) {
        model.addAttribute("list", genres);
        return "list";
    }

    /**
     * Returns a list of all the accepted troupe roles
     *
     * @param model
     * @return "list" (troupe roles)
     */
    @RequestMapping(value = "/troupe", method = RequestMethod.GET)
    public String getTroupe(Model model) {
        model.addAttribute("list", troupe);
        return "list";
    }

    /**
     * Returns a list of all the accepted cast roles
     *
     * @param model
     * @return "list" (cast roles)
     */
    @RequestMapping(value = "/cast", method = RequestMethod.GET)
    public String getCast(Model model) {
        model.addAttribute("list", cast);
        return "list";
    }

    /**
     * Returns a list of all the accepted troupe and cast roles
     *
     * @param model
     * @return "list" (troupe and cast roles)
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public String getRoles(Model model) {
        List<String> roles = new ArrayList<>();
        roles.addAll(Arrays.asList(cast));
        roles.addAll(Arrays.asList(troupe));
        model.addAttribute("list", roles);
        return "list";
    }

    /**
     * Creates a new project and sets the current user as its owner
     *
     * @param model
     * @param title
     * @param genres
     * @param plot
     * @param img
     * @param min
     * @param prizes
     * @param idTokenString
     * @return "response" (error message) in case an error occurs
     */
    @RequestMapping(value = "/newProject", method = RequestMethod.POST)
    public String addProject(Model model, @RequestParam String title, @RequestParam String genres, @RequestParam String plot, @RequestParam String img, @RequestParam long min, @RequestParam String prizes, @RequestParam String idTokenString) {
        GoogleIdToken idToken = GoogleVerifier.verify(idTokenString);
        if (idToken != null) {
            try {
                String owner = idToken.getPayload().getSubject();
                if (userService.getUserById(owner) == null) {
                    model.addAttribute("success", false);
                    model.addAttribute("response", "Creazione del progetto fallita: utente inesistente");
                } else {
                    int projectId = projectService.addProject(title, genres, plot, img, min, prizes, owner);
                    partService.addPart(projectId, owner, "Proprietario", "");
                    return getProjectById(model, projectId, idTokenString);
                }
            } catch (ConstraintViolationException e) {
                model.addAttribute("success", false);
                model.addAttribute("response", "Creazione del progetto fallita");
            }
        } else {
            model.addAttribute("success", false);
            model.addAttribute("response", "Creazione del progetto fallita: login non effettuato");
        }
        return "response";
    }

    /**
     * If the current user is the project's owner, creates a new part
     *
     * @param model
     * @param project
     * @param role
     * @param character
     * @param idTokenString
     * @return "response" (error message) in case an error occurs
     */
    @RequestMapping(value = "/part", method = RequestMethod.POST)
    public String addPart(Model model, @RequestParam int project, @RequestParam String role, @RequestParam String character, @RequestParam String idTokenString) {
        GoogleIdToken idToken = GoogleVerifier.verify(idTokenString);
        if (idToken != null) {
            try {
                Project pr = projectService.getProjectById(project);
                String userId = idToken.getPayload().getSubject();
                if (userService.getUserById(userId) == null) {
                    model.addAttribute("success", false);
                    model.addAttribute("response", "Creazione della parte fallita: utente inesistente");
                } else if (pr == null) {
                    model.addAttribute("success", false);
                    model.addAttribute("response", "Creazione della parte fallita: progetto inesistente");
                } else if (pr.getOwner().equals(userId)) {
                    addPartProperties(model, partService.addPart(project, role, character), userId);
                    return "part";
                } else {
                    model.addAttribute("success", false);
                    model.addAttribute("response", "Creazione della parte fallita: non sei il proprietario del progetto");
                }
            } catch (ConstraintViolationException e) {
                model.addAttribute("success", false);
                model.addAttribute("response", "Creazione della parte fallita");
            }
        } else {
            model.addAttribute("success", false);
            model.addAttribute("response", "Creazione della parte fallita: login non effettuato");
        }
        return "response";
    }

    /**
     * Assigns a candidate to a part
     *
     * @param model
     * @param candidacy
     * @param idTokenString
     * @return "response" (error message) in case an error occurs
     */
    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    public String assignPart(Model model, @RequestParam int candidacy, @RequestParam String idTokenString) {
        GoogleIdToken idToken = GoogleVerifier.verify(idTokenString);
        if (idToken != null) {
            try {
                Candidacy c = candidacyService.getCandidacyById(candidacy);
                if (c == null) {
                    model.addAttribute("success", false);
                    model.addAttribute("response", "Assegnazione della parte fallita: candidatura inesistente");
                    return "response";
                }
                Project pr = projectService.getProjectById(partService.getPartById(c.getPart()).getProject());
                String userId = idToken.getPayload().getSubject();
                if (pr != null && pr.getOwner().equals(userId)) {
                    addPartProperties(model, partService.updatePart(c.getPart(), c.getUser()), userId);
                    return "part";
                } else {
                    model.addAttribute("success", false);
                    model.addAttribute("response", "Assegnazione della parte fallita: non sei il proprietario del progetto");
                }
            } catch (ConstraintViolationException e) {
                model.addAttribute("success", false);
                model.addAttribute("response", "Assegnamento della parte fallita");
            }
        } else {
            model.addAttribute("success", false);
            model.addAttribute("response", "Assegnazione della parte fallita: login non effettuato");
        }
        return "response";
    }

    /**
     * Candidates the current user to the selected part
     *
     * @param model
     * @param part
     * @param idTokenString
     * @return "response" (error message) in case an error occurs
     */
    @RequestMapping(value = "/candidacy", method = RequestMethod.POST)
    public String addCandidacy(Model model, @RequestParam int part, @RequestParam String idTokenString) {
        GoogleIdToken idToken = GoogleVerifier.verify(idTokenString);
        if (idToken != null) {
            try {
                String userId = idToken.getPayload().getSubject();
                if (userService.getUserById(userId) == null) {
                    model.addAttribute("success", false);
                    model.addAttribute("response", "Creazione della candidatura fallita: utente inesistente");
                } else {
                    candidacyService.addCandidacy(part, userId);
                    addPartProperties(model, partService.getPartById(part), userId);
                    return "part";
                }
            } catch (ConstraintViolationException e) {
                model.addAttribute("success", false);
                model.addAttribute("response", "Creazione della candidatura fallita");
            }
        } else {
            model.addAttribute("success", false);
            model.addAttribute("response", "Creazione della candidatura fallita: login non effettuato");
        }
        return "response";
    }

    /**
     * Lists all the projects that have the given title as a substring of their
     * title
     *
     * @param model
     * @param title
     * @return "projectsList" (list of projects)
     */
    @RequestMapping(value = "/projects/{title}", method = RequestMethod.GET)
    public String listProjectsByTitle(Model model, @PathVariable String title) {
        model.addAttribute("projects", projectService.listProjectsByTitle(title));
        return "projectsList";
    }

    /**
     * Lists all the projects that have the given owner as a substring of their
     * owner
     *
     * @param model
     * @param owner
     * @return "projectsList" (list of projects)
     */
    @RequestMapping(value = "/projectsOwner/{owner}", method = RequestMethod.GET)
    public String listProjectsByOwner(Model model, @PathVariable String owner) {
        model.addAttribute("projects", projectService.listProjectsByOwner(owner));
        return "projectsList";
    }

    /**
     * Lists all the projects in which the given user is assigned to at least
     * one role
     *
     * @param model
     * @param collaborator
     * @return "projectsList" (list of projects)
     */
    @RequestMapping(value = "/projectsCollab/{collaborator}", method = RequestMethod.GET)
    public String listProjectsByCollaborator(Model model, @PathVariable String collaborator) {
        model.addAttribute("projects", projectService.listProjectsByCollaborator(collaborator));
        return "projectsList";
    }

    /**
     * Returns a list of projects based on the given parameters
     *
     * @param model
     * @param title
     * @param owner
     * @param genre
     * @param collab
     * @param order
     * @param asc
     * @return "projectsList" (list of projects)
     */
    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public String advancedProjectSearch(Model model, @RequestParam String title, @RequestParam String owner, @RequestParam String genre, @RequestParam String collab, @RequestParam String order, @RequestParam boolean asc) {
        model.addAttribute("projects", projectService.advancedProjectSearch(title, owner, genre, collab, order, asc));
        return "projectsList";
    }

    /**
     * Creates a new donation for the given project
     *
     * @param model
     * @param payment
     * @param idTokenString
     * @param project
     * @param amount
     * @return "response" (error message) in case an error occurs
     */
    @RequestMapping(value = "/donate", method = RequestMethod.POST)
    public String donate(Model model, @RequestParam String payment, @RequestParam String idTokenString, @RequestParam int project) {
        GoogleIdToken idToken = GoogleVerifier.verify(idTokenString);
        if (idToken == null) {
            model.addAttribute("success", false);
            model.addAttribute("response", "Donazione non effettuata: login non effettuato");
            return "reponse";
        }
        String userId = idToken.getPayload().getSubject();
        try { // Call Web Service Operation
            if (userService.getUserById(userId) == null) {
                model.addAttribute("success", false);
                model.addAttribute("response", "Donazione non effettuata: utente inesistente");
                return "response";
            }
            if (projectService.getProjectById(project) == null) {
                model.addAttribute("success", false);
                model.addAttribute("response", "Donazione non effettuata: progetto inesistente");
                return "response";
            }
            PayPalClient payPal = new PayPalClient();
            JSONObject order = payPal.getOrder(payment);
            System.out.println(order.toString(4));
            if (!"impar-seller@atm-mi.ga".equals(order.getJSONArray("purchase_units").getJSONObject(0).getJSONObject("payee").getString("email_address"))) {
                model.addAttribute("success", false);
                model.addAttribute("response", "Donazione non effettuata: la donazione non è indirizzata a Snaporaz Inc.");
                return "response";
            }
            double amount = order.getJSONArray("purchase_units").getJSONObject(0).getJSONObject("amount").getDouble("value");
            if (amount <= 0) {
                model.addAttribute("success", false);
                model.addAttribute("response", "Donazione non effettuata: l'importo deve essere superiore a 0");
                return "response";
            }
            ejb.PaymentService_Service service = new ejb.PaymentService_Service();
            ejb.PaymentService port = service.getPaymentServicePort();
            // TODO initialize WS operation arguments here

            // TODO process result here
            String result = port.addPayment(key, payment, userId, project, amount);
            if ("Donazione avvenuta con successo".equals(result)) {
                Project p = projectService.updateProject(project, amount);
                model.addAttribute("project", p);
                model.addAttribute("days", Days.daysBetween(new DateTime(), new DateTime(p.getDeadLine())).getDays());
                return "donation";
            } else {
                model.addAttribute("success", false);
                model.addAttribute("response", "Donazione già effettuata");
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass());
            System.out.println(ex.getMessage());
            model.addAttribute("success", false);
            model.addAttribute("response", "Donazione non effettuata: errore interno");
        }
        return "response";
    }
}
