# Unina Delivery - Group-ID OOBD2324_22
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-1-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->

<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->

[![All Contributors](https://img.shields.io/badge/all_contributors-1-orange.svg?style=flat-square)](#contributors-)

<!-- ALL-CONTRIBUTORS-BADGE:END -->

## Indice

- [Informazioni generali](#informazioni-generali)
  - [Membri del gruppo](#membri-del-gruppo)
- [Organizzazione del progetto](#organizzazione-del-progetto)
  - [Strumenti utilizzati](#strumenti-utilizzati)
  - [Struttura del progetto](#struttura-del-progetto)
  - [Struttura del repository](#struttura-del-repository)
  - [Gestione repository GitHub](#gestione-repository-github)
    - [Gestione branches](#gestione-branches)
      - [Workflow](#workflow)
    - [Standard dei Commit Messages](#standard-dei-commit-messages)
    - [Gestione Issues](#gestione-issues)
    - [Gestione Pull Requests](#gestione-pull-requests)
  - [Distribuzione dei ruoli](#distribuzione-dei-ruoli)
    - [Progettazione](#progettazione)
    - [Implementazione](#implementazione)
    - [Documentazione](#documentazione)
    - [Testing](#testing)

## Informazioni generali

- **Nome gruppo**: OOBD2324_22
- **Nome progetto**: Unina Delivery
- **Membri del gruppo**: [Membri del gruppo](#membri-del-gruppo)
- **Docenti referenti progetto**: [Prof. Sergio di Martino](https://www.docenti.unina.it/#!/professor/53455247494f4449204d415254494e4f444d5253524737364232364638333952/riferimenti), [Prof. Mara Sangiovanni](https://www.docenti.unina.it/#!/professor/4d41524153414e47494f56414e4e49534e474d524137354c34314638333949/riferimenti)

### Membri del gruppo

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/RiccardoElena"><img src="https://avatars.githubusercontent.com/u/23059036?v=4?s=100" width="100px;" alt="Riccardo Elena"/><br /><sub><b>Riccardo Elena</b></sub></a><br /><a href="https://github.com/RiccardoElena/UninaDelivery/commits?author=RiccardoElena" title="Code">💻</a> <a href="#ideas-RiccardoElena" title="Ideas, Planning, & Feedback">🤔</a> <a href="#content-RiccardoElena" title="Content">🖋</a> <a href="https://github.com/RiccardoElena/UninaDelivery/commits?author=RiccardoElena" title="Documentation">📖</a></td>
    </tr>
  </tbody>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->

## Organizzazione del progetto

### Strumenti utilizzati

- GitHub ([repository](https://www.youtube.com/watch?v=p7YXXieghto))
- Visual Studio Code
- Discord
- Latex
- PostgreSQL (localhost)

### Struttura del progetto

Da definire in seguito ad assegnazione traccia

### Struttura del repository

- `oo/`: cartella relativa al progetto di Object Orientation.
  - `oo/docs`: documentazione del progetto di Object Orientation.
    - `Documentazione-ObjectOrientation-GroupID.pdf`: documentazione del progetto di Object Orientation.
  - `oo/src/`: codice sorgente del progetto di Object Orientation.
  - `oo/pom.xml`: file di configurazione Maven.
- `db/`: cartella relativa al progetto di Basi di Dati.
  - `db/docs`: documentazione del progetto di Basi di Dati.
    - `Documentazione-BasiDiDati-GroupID.pdf`: documentazione del progetto di Basi di Dati.
  - `db/src/`: codice sorgente del progetto di Basi di Dati.
    - `README.md`: README del progetto di Basi di Dati.
- `README.md`: README generale del progetto, questo file.
- `sun_checks.xml`: file di configurazione Checkstyle.

### Gestione repository GitHub

#### Gestione branches

- `master`: branch principale
- `develop`: branch di sviluppo
- `feature-NOMEFEATURE/`: branch facoltativa per feature extra

##### Workflow

Struttura generale di ereditarietà

`feature-NOMEFEATURE -> develop -> main` (tramite pull request e code review per main).

- _Gitflow_ `feature-NOMEFEATURE -> develop`: ogni membro del gruppo può creare un branch per sviluppare una feature extra, che verrà poi integrata nel branch `develop` tramite pull request e code review.

- _Parallel Branches_ `develop -> main`: il branch `develop` sarà parallelo al branch `main` in cui verrà integrato periodicamente tramite pull request e code review.

#### Standard dei Commit Messages

Si utilizza lo standard fornito da [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) eventualmente adattato per il progetto.

#### Gestione Issues

Di persona o tramite Discord.

#### Gestione Pull Requests

Approvazione di tutti i collaboratori del progetto necessaria all'approvazione.

### Distribuzione dei ruoli

#### Progettazione

Per quanto riguarda l'intera fase di progettazione sia di Basi di Dati che di Object Orientation, verrà svolta in maniera collaborativa da tutti i membri del gruppo.

#### Implementazione

Suddivisione equa delle feature da implementare tra tutti i membri del gruppo **_successiva alla fase di progettazione_**.

#### Documentazione

Ogni membro si farà carico di aggiornare la documentazione relativa alla parte di progetto di cui si è occupato o della feature che ha implementato.

#### Testing

Ogni membro si farà carico di testare in autonomia **TUTTO** il progetto nello stato corrente e di riportare agli altri collaboratori eventuali _warnings_, **errors**, o **_BUG_** trovati.

## Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):
<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->
<!-- ALL-CONTRIBUTORS-LIST:END -->

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://GitHub.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!
