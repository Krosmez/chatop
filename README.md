# API ChâTop

Application de gestion de locations immobilières avec authentification JWT.

## Prérequis

- Java 11 ou supérieur
- MySQL 8.0 ou supérieur
- Maven 3.6 ou supérieur

## Installation du projet

### 1. Récupérer le code source

Clonez le repository ou téléchargez le code source du projet.

```bash
git clone [url-du-repository]
cd ChaTop
```

### 2. Configuration de la base de données

#### Créer la base de données MySQL

Connectez-vous à MySQL et créez la base de données :

```sql
CREATE
DATABASE chat_db;
```

#### Configurer les accès à la base de données

L'application utilise des variables d'environnement pour se connecter à la base de données.

- `DB_URL` = `VOTRE_URL_DE_BDD_SQL`
- `DB_USERNAME` = `TEST_DEV`
- `DB_PASSWORD` = `TEST_DEV`

**Note :** Remplacez `TEST_DEV` par vos identifiants MySQL si nécessaire.

#### Créer un utilisateur MySQL (optionnel)

Si vous souhaitez utiliser l'utilisateur `TEST_DEV` comme configuré, créez-le dans MySQL :

```sql
CREATE
USER 'TEST_DEV'@'localhost' IDENTIFIED BY 'TEST_DEV';
GRANT ALL PRIVILEGES ON chat_db.* TO
'TEST_DEV'@'localhost';
FLUSH
PRIVILEGES;
```

### 3. Installer les dépendances

Maven téléchargera automatiquement toutes les dépendances nécessaires :

```bash
mvn clean install
```

### 4. Lancer l'application

Démarrez l'application avec Maven :

```bash
mvn spring-boot:run
```

L'application sera accessible sur `http://localhost:8080`

## Documentation de l'API

### Accéder à la documentation Swagger

Une fois l'application démarrée, vous pouvez consulter la documentation interactive de l'API :

**URL :** http://localhost:8080/swagger-ui.html

Cette interface vous permet de :

- Voir toutes les routes disponibles
- Tester les endpoints directement depuis le navigateur
- Consulter les exemples de requêtes et réponses

## Configuration de l'application

Ajouter cette option à votre VM : `-Dspring.profiles.active=local`.

Ensuite, créez un fichier `application-local.properties` dans le dossier `src/main/resources` avec les paramètres
suivants :

- `spring.datasource.url`=votre_url_de_bdd_sql
- `spring.datasource.username`=votre_username_mysql
- `spring.datasource.password`=votre_password_mysql
- `app.jwtSecret`=votre_secret_jwt
- `spring.servlet.multipart.location`=votre_dossier_de_stockage_des_images

## Utiliser l'API

### Authentification

L'API utilise des tokens JWT pour sécuriser les accès.

#### Routes publiques (sans authentification)

- `POST /api/auth/register` - Créer un compte
- `POST /api/auth/login` - Se connecter
- `GET /api/images/{filename}` - Récupérer une image

#### Routes protégées (authentification requise)

Toutes les autres routes nécessitent un token JWT dans le header de la requête :

```
Authorization: Bearer votre_token_jwt
```

### Tester l'API avec Swagger

1. Démarrez l'application
2. Ouvrez http://localhost:8080/swagger-ui.html dans votre navigateur
3. Utilisez la route `/api/auth/register` ou `/api/auth/login` pour obtenir un token
4. Cliquez sur le bouton "Authorize" en haut de la page
5. Collez votre token et validez
6. Vous pouvez maintenant tester toutes les routes de l'API

## Structure du projet

```
src/main/java/com/chatop/
├── config/              Configuration Spring Security, Swagger, etc.
├── controller/          Points d'entrée de l'API (routes)
├── service/             Logique métier
├── repository/          Accès aux données (JPA)
├── entity/              Modèles de données (tables)
├── dto/                 Objets de transfert de données
├── exception/           Gestion des erreurs
├── security/            JWT et authentification
└── utils/               Utilitaires et constantes
```

## Résolution de problèmes

### L'application ne démarre pas

Vérifiez que :

- MySQL est démarré
- La base de données `chat_db` existe
- Les variables d'environnement sont correctement définies
- Le port 8080 n'est pas déjà utilisé

### Erreur de connexion à la base de données

Vérifiez les identifiants MySQL dans vos variables d'environnement :

- `DB_USERNAME` doit correspondre à un utilisateur MySQL existant
- `DB_PASSWORD` doit être le bon mot de passe
- L'utilisateur doit avoir les droits sur la base `chat_db`

### Les images ne s'affichent pas

Vérifiez que le dossier `uploads` existe à la racine du projet. Il sera créé automatiquement au premier upload.
