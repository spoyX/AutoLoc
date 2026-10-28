# Notes Atelier 5 — API REST et Swagger

## Section A — Endpoints
| Contrôleur | Verbe + Route | Succès | Erreurs |
|------------|---------------|--------|---------|
| ContratController | GET /api/contrats | 200 | — |
| ContratController | GET /api/contrats/{id} | 200 | 404 |
| ContratController | POST /api/contrats | 201 | 400 |
| ContratController | PUT /api/contrats/{id} | 200 | 404, 400 |
| ContratController | DELETE /api/contrats/{id} | 204 | 404 |
| PaiementController | GET /api/paiements | 200 | — |
| PaiementController | GET /api/paiements/{id} | 200 | 404 |
| (Idem pour Agences, Employes, Vehicules, Equipements, Clients, Reservations, Maintenances) | ... | ... | ... |

## Section B — Annotations Jackson
| Association | Annotation | Justification |
|-------------|------------|---------------|
| `Contrat.paiements` | `@JsonManagedReference("contrat-paiements")` | Composition : on veut voir les paiements dans le contrat. |
| `Paiement.contrat` | `@JsonBackReference("contrat-paiements")` | Casse la boucle infinie côté enfant. |
| `Agence.vehicules` | `@JsonIgnore` | Évite d'afficher 1000 véhicules quand on liste les agences. |
| `Reservation.client` | *(aucune)* | On en a besoin pour le POST. |
| `Client.reservations` | `@JsonIgnore` | Évite la boucle infinie. |

## Section C — Postman et Swagger
- **Postman** : Utilisé pour tester les endpoints (GET, POST, PUT, DELETE), vérifier les codes d'erreur (400, 404) et automatiser avec des scripts (`pm.test`).
- **Swagger UI** : Interface générée automatiquement à partir du code grâce aux annotations (`@Tag`, `@Operation`). Permet de documenter l'API et de la tester directement depuis le navigateur via "Try it out".

## Section D — Anomalies SonarQube
| # | Fichier | Anomalie | Règle | Correction |
|---|---------|----------|-------|------------|
| 1 | ContratController | Import inutile | S1128 | Remplacé par un import précis. |
| 2 | ContratController | Injection par attribut (`@Autowired`) | S6813 | Passé en `@RequiredArgsConstructor`. |
| 3 | ContratController | Exposition directe de l'entité | (Bonne pratique) | À remplacer par des DTO (Chapitre 10). |

## Section E — Réponses aux questions
1. **Quel composant reçoit toutes les requêtes ?** Le `DispatcherServlet`. Il utilise le `HandlerMapping` pour trouver la bonne méthode.
2. **Pourquoi dépendre de `IContratService` et non du Repository ?** Pour respecter les couches (Controller → Service → Repository) et le principe de ségrégation des responsabilités.
3. **Pourquoi un POST renvoie 201 et un DELETE 204 ?** 201 (Created) indique une création réussie. 204 (No Content) indique un succès sans contenu à retourner. 404 = ressource métier absente, 500 = erreur technique.
4. **Pourquoi `PaiementController` n'a-t-il que des GET ?** Car les paiements sont gérés en cascade par le Contrat (règle métier). Un POST sur `/api/paiements` renvoie 405 (Method Not Allowed).
5. **Risques d'exposer les entités JPA ?** Fuite d'informations, couplage API/BDD. `Contrat` utilise `@JsonManagedReference` car c'est une composition. `Agence` utilise `@JsonIgnore` pour ne pas surcharger le JSON (agrégation).