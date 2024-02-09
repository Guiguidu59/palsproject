package com.example.demo.controller;

import com.example.demo.beans.Pals;
import com.example.demo.beans.Skill;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/pals")
public class PalsController {

    private final MongoTemplate mongoTemplate;

    public PalsController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping()
    public ResponseEntity<?> getPals() {
        try {
            List<Pals> pals = mongoTemplate.findAll(Pals.class);
            String rep = "Voici la liste des pals :\n";
            for (Pals pal : pals) {
                rep += pal.toString()+"\n";
            }
            return ResponseEntity.status(HttpStatus.FOUND).body(rep);
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Vous n'avez pas pu afficher les pals :\nErreur : "+e.toString());
        }
    }

    @GetMapping("remove")
    public ResponseEntity<?> remmoveAll() {
        List<Pals> pals = mongoTemplate.findAll(Pals.class);
        for (Pals pal : pals) {
            mongoTemplate.remove(pal);
        }
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping("savePal")
    public ResponseEntity savePal(@RequestBody Pals pal) {
        try {
            mongoTemplate.save(pal);
            return ResponseEntity.status(HttpStatus.OK).body("Vous avez ajouté un nouveau pal :\n"+pal.toString());
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Vous n'avez pas pu ajouter un nouveau pal :\nErreur : "+e.toString());
        }
    }

    @PostMapping("savePals")
    public ResponseEntity savePals(@RequestBody List<Pals> pals) {
        try {
            String rep = "";
            for (Pals pal : pals) {
                rep += pal.toString()+"\n";
                mongoTemplate.save(pal);
            }
            return ResponseEntity.status(HttpStatus.OK).body("Vous avez ajouté de nouveaux pals :\n"+rep);
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Vous n'avez pas pu ajouter un nouveau pal :\nErreur : "+e.toString());
        }
    }

    @GetMapping("getByID/{id}")
    public ResponseEntity getPlasById(@PathVariable int id) {
        try {
            Pals pal = mongoTemplate.findById(id, Pals.class);
            if (pal == null) {
                return ResponseEntity.status(HttpStatus.OK).body("Ce pal n'existe pas (id = "+id+")");
            }
            return ResponseEntity.status(HttpStatus.OK).body("Voici le pal (id = "+id+") :\n"+pal.toString());
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Vous n'avez pas pu récupérer le pal (id = "+id+")\nErreur : "+e.toString());
        }
    }

    @GetMapping("getByName/{name}")
    public ResponseEntity getPlasByName(@PathVariable String name) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("name").is(name));
            List<Pals> pals = mongoTemplate.find(query, Pals.class);
            String rep = "";
            for (Pals pal : pals) rep += pal.toString()+"\n";
            return ResponseEntity.status(HttpStatus.OK).body("Voici les pals (name = "+name+") :\n"+rep);
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Vous n'avez pas pu récupérer les pals (name = "+name+")\nErreur : "+e.toString());
        }
    }

    @GetMapping("getByType/{type}")
    public ResponseEntity getPlasByType(@PathVariable String type) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("types").is(type));
            List<Pals> pals = mongoTemplate.find(query, Pals.class);
            String rep = "";
            for (Pals pal : pals) rep += pal.toString()+"\n";
            return ResponseEntity.status(HttpStatus.OK).body("Voici les pals (type = "+type+") :\n"+rep);
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Vous n'avez pas pu récupérer les pals (type = "+type+")\nErreur : "+e.toString());
        }
    }

    @PostMapping("saveNewPal")
    public ResponseEntity saveNewPal(@RequestBody Pals pal) {
        try {
            mongoTemplate.save(pal);
            return ResponseEntity.status(HttpStatus.OK).body("Vous avez ajouté un nouveau pal :\n"+pal.toString());
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Vous n'avez pas pu ajouter un nouveau pal :\nErreur : "+e.toString());
        }
    }

    @GetMapping("getSkills/{id}")
    public ResponseEntity getSkills(@PathVariable int id) {
        try {
            Pals pal = mongoTemplate.findById(id, Pals.class);
            if (pal == null) {
                return ResponseEntity.status(HttpStatus.OK).body("Ce pal n'existe pas (id = "+id+")");
            }
            String rep = "";
            for (Skill skill : pal.getSkills()) rep += skill.toString()+"\n";
            return ResponseEntity.status(HttpStatus.OK).body("Voici les skills du pal (id = "+id+") :\n"+rep);
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Vous n'avez pas pu récupérer les skills du pal (id = "+id+") :\nErreur : "+e.toString());
        }
    }

    @PostMapping("addSkill/{id}")
    public ResponseEntity addSkill(@PathVariable int id, @RequestBody Skill skill) {
        try {
            Pals pal = mongoTemplate.findById(id, Pals.class);
            if (pal == null) {
                return ResponseEntity.status(HttpStatus.OK).body("Ce pal n'existe pas (id = "+id+")");
            }
            pal.addSkill(skill);
            mongoTemplate.save(pal);
            return ResponseEntity.status(HttpStatus.OK).body("Vous avez ajouté un skill au pal (id = "+id+") :\nNouveau skill ajouté : "+skill.toString());
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Vous n'avez pas réussi à ajouter un skill au pal (id = "+id+") :\nErreur : "+e.toString());
        }
    }

    @PutMapping("modifySkill/{id}/{skillName}")
    public ResponseEntity addSkill(@PathVariable int id, @PathVariable String skillName, @RequestBody Skill updatedSkill) {
        try {
            Pals pal = mongoTemplate.findById(id, Pals.class);
            if (pal == null) {
                return ResponseEntity.status(HttpStatus.OK).body("Ce pal n'existe pas (id = "+id+")");
            }

            Skill skillToUpdate = null;
            for (Skill skill : pal.getSkills()) {
                if (skill.getName().equals(skillName)) {
                    skillToUpdate = skill;
                    break;
                }
            }

            if (skillToUpdate != null) {
                skillToUpdate.setName(updatedSkill.getName());
                skillToUpdate.setType(updatedSkill.getType());
                skillToUpdate.setCooldown(updatedSkill.getCooldown());
                skillToUpdate.setPower(updatedSkill.getPower());
                skillToUpdate.setDescription(updatedSkill.getDescription());

                mongoTemplate.save(pal);
                return ResponseEntity.status(HttpStatus.OK).body("Skill du pal modifé (id = "+id+") :\n"+skillToUpdate.toString());
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("Ce skill est inexistant pas (name = "+skillName+")");
            }
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Vous n'avez pas réussi à ajouter un skill au pal (id = "+id+") :\nErreur : "+e.toString());
        }
    }

    @GetMapping("getTypes/{id}")
    public ResponseEntity getTypes(@PathVariable int id) {
        try {
            Pals pal = mongoTemplate.findById(id, Pals.class);
            if (pal == null) {
                return ResponseEntity.status(HttpStatus.OK).body("Ce pal n'existe pas (id = "+id+")");
            }
            String rep = "";
            for (String type : pal.getTypes()) {
                rep += type+"\n";
            }
            return ResponseEntity.status(HttpStatus.OK).body("Voici les types du pal (id = "+id+") :\n"+rep);
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Vous n'avez pas réussi à ajouter un skill au pal (id = "+id+") :\nErreur : "+e.toString());
        }
    }

    @PostMapping("addType/{id}/{type}")
    public ResponseEntity addType(@PathVariable int id,@PathVariable String type) {
        try {
            Pals pal = mongoTemplate.findById(id, Pals.class);
            if (pal == null) {
                return ResponseEntity.status(HttpStatus.OK).body("Ce pal n'existe pas (id = "+id+")");
            }
            pal.addType(type);
            mongoTemplate.save(pal);
            return ResponseEntity.status(HttpStatus.OK).body("Vous avez ajouté un type au pal (id = "+id+") :\n"+type);
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Vous n'avez pas réussi à ajouter un skill au pal (id = "+id+") :\nErreur : "+e.toString());
        }
    }

    @PostMapping("addTypes/{id}")
    public ResponseEntity addType(@PathVariable int id, @RequestBody List<String> types) {
        try {
            Pals pal = mongoTemplate.findById(id, Pals.class);
            if (pal == null) {
                return ResponseEntity.status(HttpStatus.OK).body("Ce pal n'existe pas (id = "+id+")");
            }
            String rep = "";
            for (String type : types) {
                pal.addType(type);
                rep += type+"\n";
            }
            mongoTemplate.save(pal);
            return ResponseEntity.status(HttpStatus.OK).body("Vous avez ajouté des types au pal (id = "+id+") :\n"+rep);
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Vous n'avez pas réussi à ajouter de type au pal (id = "+id+") :\nErreur : "+e.toString());
        }
    }

    @DeleteMapping("removeType/{id}/{type}")
    public ResponseEntity removeType(@PathVariable int id, @PathVariable String type) {
        try {
            Pals pal = mongoTemplate.findById(id, Pals.class);
            if (pal == null) {
                return ResponseEntity.status(HttpStatus.OK).body("Ce pal n'existe pas (id = "+id+")");
            }
            pal.getTypes().remove(type);
            mongoTemplate.save(pal);
            return ResponseEntity.status(HttpStatus.OK).body("Vous avez réussi à supprimé un type au pal (id = "+id+") :\n"+type);
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Vous n'avez pas réussi à supprimé un type au pal (id = "+id+") :\nErreur : "+e.toString());
        }
    }

    @GetMapping("getPalsSortedByRarity/{direction}")
    public ResponseEntity<?> getPalsSortedByRarity(@PathVariable String direction) {
        Sort sortByRarity;
        try {
            if (direction.equals("desc")) {
                sortByRarity = Sort.by(Sort.Direction.DESC, "rarity");
            } else if (direction.equals("asc")) {
                sortByRarity = Sort.by(Sort.Direction.ASC, "rarity");
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Mauvais chemin essayé avec \"desc\" ou \"asc\"");
            }
            List<Pals> pals = mongoTemplate.find(new Query().with(sortByRarity), Pals.class);
            String rep = "Voici la liste des pals triée par rarity :\n";
            for (Pals pal : pals) {
                rep += pal.toString()+"\n";
            }
            return ResponseEntity.status(HttpStatus.FOUND).body(rep);
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Vous n'avez pas pu afficher les pals triés par rarity :\nErreur : "+e.toString());
        }
    }

    @GetMapping("getPalsSortedByPrice/{direction}")
    public ResponseEntity<?> getPalsSortedByPrice(@PathVariable String direction) {
        Sort sortByRarity;
        try {
            if (direction.equals("desc")) {
                sortByRarity = Sort.by(Sort.Direction.DESC, "price");
            } else if (direction.equals("asc")) {
                sortByRarity = Sort.by(Sort.Direction.ASC, "price");
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Mauvais chemin essayé avec \"desc\" ou \"asc\"");
            }
            List<Pals> pals = mongoTemplate.find(new Query().with(sortByRarity), Pals.class);
            String rep = "Voici la liste des pals triée par price :\n";
            for (Pals pal : pals) {
                rep += pal.toString()+"\n";
            }
            return ResponseEntity.status(HttpStatus.FOUND).body(rep);
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Vous n'avez pas pu afficher les pals triés par price :\nErreur : "+e.toString());
        }
    }
}
