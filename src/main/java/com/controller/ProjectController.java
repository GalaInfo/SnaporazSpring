package com.controller;

import com.extra.GoogleVerifier;
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

    /*
     403 Forbidden
    
     @RequestMapping(value = "/pay", method = RequestMethod.GET)
     public void pay(Model model) {
     final String uri = "https://api.sandbox.paypal.com/v2/checkout/orders/4HG34491JH664093N";

     final HttpHeaders headers = new HttpHeaders();
     headers.set("Content-Type", "application/json");
     headers.set("Authorization", "Bearer A21AAGgcovPkTBIeeqKZ-b16YF5w2W4QOAYRDe6gmSGtMA2xUXV88bY3EysRkcXpLce4LYC9UTc-QlzuFxmj4Ipy6cDeNWBvw");
     headers.set("User-Agent", "Snaporaz");

     //Create a new HttpEntity
     HttpEntity entity = new HttpEntity(headers);

     RestTemplate restTemplate = new RestTemplate();
        
     //Execute the method writing your HttpEntity to the request
     try{
     ResponseEntity<Map> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Map.class);
     System.out.println(response.getBody());
     }catch(HttpClientErrorException e){
     System.out.println(e.getMessage());
     }
        
     }
     */
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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainPage(Model model) {
        model.addAttribute("mostFoundedProjects", projectService.listMostFoundedProjects());
        model.addAttribute("mostRecentProjects", projectService.listMostRecentProjects());
        model.addAttribute("closestProjects", projectService.listClosestProjects());
        return "home";
    }

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

    @RequestMapping(value = "/genres", method = RequestMethod.GET)
    public String getGenres(Model model) {
        model.addAttribute("list", genres);
        return "list";
    }

    @RequestMapping(value = "/troupe", method = RequestMethod.GET)
    public String getTroupe(Model model) {
        model.addAttribute("list", troupe);
        return "list";
    }

    @RequestMapping(value = "/cast", method = RequestMethod.GET)
    public String getCast(Model model) {
        model.addAttribute("list", cast);
        return "list";
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public String getRoles(Model model) {
        List<String> roles = new ArrayList<>();
        roles.addAll(Arrays.asList(cast));
        roles.addAll(Arrays.asList(troupe));
        model.addAttribute("list", roles);
        return "list";
    }

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

    @RequestMapping(value = "/projects/{title}", method = RequestMethod.GET)
    public String listProjectsByTitle(Model model, @PathVariable String title) {
        model.addAttribute("projects", projectService.listProjectsByTitle(title));
        return "projectsList";
    }

    @RequestMapping(value = "/projectsOwner/{owner}", method = RequestMethod.GET)
    public String listProjectsByOwner(Model model, @PathVariable String owner) {
        model.addAttribute("projects", projectService.listProjectsByOwner(owner));
        return "projectsList";
    }

    @RequestMapping(value = "/projectsCollab/{collaborator}", method = RequestMethod.GET)
    public String listProjectsByCollaborator(Model model, @PathVariable String collaborator) {
        model.addAttribute("projects", projectService.listProjectsByCollaborator(collaborator));
        return "projectsList";
    }

    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public String advancedProjectSearch(Model model, @RequestParam String title, @RequestParam String owner, @RequestParam String genre, @RequestParam String collab, @RequestParam String order, @RequestParam boolean asc) {
        model.addAttribute("projects", projectService.advancedProjectSearch(title, owner, genre, collab, order, asc));
        return "projectsList";
    }

    @RequestMapping(value = "/donate", method = RequestMethod.POST)
    public String donate(Model model, @RequestParam String payment, @RequestParam String idTokenString, @RequestParam int project, @RequestParam double amount) {
        GoogleIdToken idToken = GoogleVerifier.verify(idTokenString);
        if (idToken == null) {
            model.addAttribute("success", false);
            model.addAttribute("response", "Donazione non effettuata: login non effettuato");
        }
        String userId = idToken.getPayload().getSubject();
        try { // Call Web Service Operation
            if (userService.getUserById(userId) == null) {
                model.addAttribute("success", false);
                model.addAttribute("response", "Donazione non effettuata: utente inesistente");
            }
            if (projectService.getProjectById(project) == null) {
                model.addAttribute("success", false);
                model.addAttribute("response", "Donazione non effettuata: progetto inesistente");
            }
            if (amount <= 0) {
                model.addAttribute("success", false);
                model.addAttribute("response", "Donazione non effettuata: l'importo deve essere superiore a 0");
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
            model.addAttribute("success", false);
            model.addAttribute("response", "Donazione non effettuata: errore interno");
        }
        return "response";
    }
}
