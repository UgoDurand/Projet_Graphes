Pour mettre à jour les différentes branches après un merge ou un update du main

1. Mettre à jour votre branche de développement :
    - git add
    - git commit
    - git push

2. Mettre à jour le main local :
    - Changer de branche et repasser sur la main
    - git pull origin main

3. Mettre à jour la branche de développement :
    - Changer de branche pour passer sur la branche personelle
    - git rebase main
    - git push origin nom_branche --force

4. Mise à jour récupérée !

## Installation et Exécution

### Pré-requis

Assurez-vous que les éléments suivants sont installés sur votre système :

- **Java JDK** 8 ou supérieur (idéalement la version 11+)
- **JavaFX SDK** (si le projet inclut des bibliothèques JavaFX externes)

### Clonage du projet

Clonez le dépôt GitHub pour récupérer le projet sur votre machine :

```bash
git clone https://github.com/UgoDurand/Projet_Graphes.git
cd Projet_Graphes

