# Citronix 🌿🍋

**Citronix** est une application de gestion conçue pour optimiser les opérations des fermes de citrons. Ce projet permet aux fermiers de suivre la production, la récolte et la vente des produits tout en assurant une gestion efficace des ressources agricoles.

---

## 🚀 Fonctionnalités Principales

### **Gestion des Fermes**
- Créer, modifier et consulter des fermes avec des détails tels que nom, localisation, superficie et date de création.
- Recherche multicritère avancée.

### **Gestion des Champs**
- Associer des champs à une ferme avec des contraintes de superficie.
- Validation stricte :
   - Superficie minimale : **0.1 hectare**.
   - Superficie maximale : **50 %** de la ferme.
   - Limitation : **10 champs maximum par ferme**.

### **Gestion des Arbres**
- Suivre les arbres avec leur âge, champ d’appartenance et productivité.
- Calcul automatique de la productivité annuelle :
   - **Jeune (< 3 ans)** : 2,5 kg/saison.
   - **Mature (3-10 ans)** : 12 kg/saison.
   - **Vieux (> 10 ans)** : 20 kg/saison.
- Densité maximale : **100 arbres/hectare**.
- Période de plantation : **mars à mai**.

### **Gestion des Récoltes**
- Suivre les récoltes par saison (hiver, printemps, été, automne).
- Assurer qu'un arbre ne soit inclus qu'une seule fois par saison.

### **Gestion des Ventes**
- Enregistrer les ventes avec la date, le client, le prix unitaire et la récolte associée.
- Calcul automatique du revenu :  
  **Revenu = quantité × prix unitaire**.

---

## 📋 Contraintes

### **Superficies et Champs**
- Superficie minimale : **0.1 hectare**.
- Superficie maximale : **50 %** de la ferme.

### **Arbres et Récoltes**
- Limite d'âge des arbres : **20 ans** (non productifs au-delà).
- Une seule récolte par champ et par saison.
- Plantation uniquement entre **mars et mai**.

---

## 🛠️ Technologies Utilisées

- **Framework** : Spring Boot
- **Base de données** : Postgress
- **Gestion des entités** : Lombok, Builder Pattern
- **Validation des données** : Annotations Spring
- **Tests** : JUnit, Mockito
- **Conversion de données** : MapStruct

---

## 📂 Structure du Projet

### **Architecture en Couches**
1. **Controller** : Gestion des requêtes API.
2. **Service** : Logique métier.
3. **Repository** : Accès à la base de données.
4. **Entity** : Modélisation des données.

---

## 🚧 Installation

### **Prérequis**
- **Java 17+**
- **Maven** ou **Gradle**
- **Postgress**
- **liquibase**
### **Étapes d’Installation**
1. Clonez le dépôt :
   ```bash
   git clone https://github.com/EttabetAicha/Citronix
   cd citronix
