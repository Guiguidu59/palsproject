# Utilisez une image de base avec Java préinstallé
FROM openjdk:21

# Définissez le répertoire de travail dans le conteneur
WORKDIR /pals_appli

# Copiez le fichier JAR Maven (généralement dans le dossier target/) dans le conteneur
COPY target/demo-0.0.1-SNAPSHOT.jar /pals_appli

# Commande pour exécuter l'application Java
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]