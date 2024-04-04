# Spam Text Repository (STRep)
STRep is a repository management sofware providing advanced features for sharing spam filteirng corpora/datasets. STRep allows to easilly share and manage tweets, youtube commnts, e-mail (.eml), Web Archives (WARC) and SMS labelled datasets. This software incorporates some ideas to solve some known problems:
* privacy issues
* licensing issues
* pre-processing of data

STRep implements all these functionalities:

**Storage, share and management of system datasets comprising contents belonging to different communication protocols**

A system dataset can be easily created into STRep by defining its properties (*name*, *description*, *license*, *citation*, *DOI* and *visibility*) and uploading a zip file organized in two subdirectories: *ham*, that must contain legitimate content files and *spam*, which contains spam files. STRep supports system datasets containing e-mails (RFC 5322), websites (Web Archive, WARC), tweets, Youtube comments, SMS (Short Message Service) or plain text. The uploading of a system dataset implies the automatic extraction of instance metadata (language, date and file type) mainly used for the creation of tuned datasets. In order to avoid the inclusion of any data that could be used for the identification of content authors and the building of any form of digital profiles of them, STRep administrators will receive an e-mail each time a system dataset is uploaded. The administrators will act as data curator/validator and ensure new system dataset does not contain any data that could damage user rights. Otherwise, an administrator could contact the dataset owner to proceed with fixing of the found troubles.

**Creation, storage and management of tuned datasets by combination/filtering of existing system corpora**

Additionally, STRep allows to create tuned datasets by combining/filtering contents extracted from system datasets. Besides combining instances of system datasets, it allows to filter instances by language, date, type of content and license. The definition of a tuned dataset also involves gathering of the *name*, *description*, *citation*, *visibility*, *DOI* and *license* dataset attributes.

**Automating text pre-processing to create different views of tuned datasets**

Any dataset can be pre-processed as many times as necessary to create views required for the application and comparison of different techniques. I

**Publication of tuned datasets**

STRep allows publishing system and tuned datasets allowing the user to restrict access to them, through a permissions list defined in the application: *public*, *protected* and *private*. A *public* dataset can be accessed by every user even without being registered. A *protected* dataset can be accessed only by registered users. Finally, *private* datasets are only accessible by the user who created or upload them.

**Dataset license management**

STRep is also capable of assisting users in the assignation of licenses for the new tuned datasets. It includes by default a licenses list and allows the management of all kinds of licenses and their rights. System datasets must be associated with a specific license defined in the platform. Furthermore, as can be easily depicted, the definition of new tuned corpora is limited by the licenses of system datasets combined. Before combining datasets from multiple sources, we should check if licenses of selected datasets guarantee their utilization as sources of the new tuned dataset. As shown in Figure 1a, datasets that do not allow the adaptation should not be combined with other ones. On the other hand, the combination of datasets whose original licenses do not allow their redistribution with other licenses will be only possible when the original licenses of combined datasets are the same. 

![STRep licensing checks](https://moncho.mdez-reboredo.info/imgstrep/Figure1a.png)

*Figure 1a. Determine if two system datasets having licences L1 and L2 can be combined into a new tuned dataset*

The selection of several datasets to combine into a tuned dataset could also introduce several limitations in the visibility and possible licenses of the target dataset. In detail, visibility of generated tuned datasets should be limited to private when it contains instances obtained from system datasets whose license prohibits their redistribution. See checking process in Figure 1b.


![STRep licensing checks](https://moncho.mdez-reboredo.info/imgstrep/Figure1b.png)

*Figure 1b. Check if visibility of a tuned dataset should be restricted*


Figure 1c represents an algorithm that calculates the licenses that can be assigned to a tuned dataset from a complete list of licenses included in the platform

![STRep licensing checks](https://moncho.mdez-reboredo.info/imgstrep/Figure1c.png)

*Figure 1c. Check possible licenses for a new tuned dataset*

The following is a class diagram, which describes the architecture of the system:


![Class diagram](https://moncho.mdez-reboredo.info/imgstrep/Figure2.png)

*Figure 2. Class diagram*

## Customizing

To customize this application, you can find the corresponding files in *resources* directory:

  * The image files are in */images/*. This directory contains all image files.
  * The favicon file is  */favicon.ico*.
  * The text files are in */locale*. You will find a file per language with all texts.

## Research Data for Spam (rdata4spam)

STRep is part of a broader project: rdata4spam, that is a client-server application accessible through a web browser. 

Considering the time required to execute some repository actions, most of them have been implemented as tasks that are executed in a separate application called STRep Service (https://github.com/sing-group/strep_service). This architecture together form along rdata4spam.

![rdata4spam architecture](https://moncho.mdez-reboredo.info/imgstrep/Figure3.png)

Figure 3. rdata4spam architecture

To test the software, it has been deployed in https://rdata.4spam.group.

## Authors

This project has been conceived and developed by SING research group. The development team is composed by:

  * Ismael Vázquez: Developer
  * José R. Méndez: Subject Matter Expert
  * María Novo: Software Architecture Design and Team Leader
  * Reyes Pavón: Developer
  * Rosalía Laza: Developer
  * David Ruano: Developer

## License

Copyright (C) 2018 Sing Group (University of Vigo)

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program. If not, see https://www.gnu.org/licenses/.

## Citing

Please, cite the following publication if you use STRep:

I. Vázquez; M. Novo-Lourés; R. Pavón; R. Laza; J. R. Méndez; D. Ruano-Ordás (2023) Improvements for Research Data Repositories: the case of text spam. Journal of Information Science. Volume 49, Issue 2, pp. 285–301. ISSN: (Online) 1741-6485 - (Print) 0165-5515. DOI: [https://doi.org/10.1177/0165551521998636](https://doi.org/10.1177/0165551521998636)