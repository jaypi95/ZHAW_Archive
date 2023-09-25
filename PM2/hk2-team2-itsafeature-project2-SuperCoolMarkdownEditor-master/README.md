[![SuperCoolMarkDown](doc/logo.png)](https://github.zhaw.ch/PM2-IT20taWIN-bles-mach-kars/hk2-team2-itsafeature-project2-SuperCoolMarkdownEditor)

[Download](https://github.zhaw.ch/PM2-IT20taWIN-bles-mach-kars/hk2-team2-itsafeature-project2-SuperCoolMarkdownEditor#-download) |
[Feedback](https://github.zhaw.ch/PM2-IT20taWIN-bles-mach-kars/hk2-team2-itsafeature-project2-SuperCoolMarkdownEditor#%EF%B8%8F-feedback)

![GitHub labels](https://img.shields.io/badge/Version-v1.0.0-blue)
![GitHub labels](https://img.shields.io/badge/Language-Java-orange)
![Maintenance](https://img.shields.io/maintenance/yes/2021)
![GitHub labels](https://img.shields.io/badge/Ask%20us-anything-blue)

SuperCoolMarkDown is a project that seeks to provide an open-source alternative to the Microsoft Office suit in order to:
- Prevent Microsoft and other corporations from taking control over the market
- Keep it as simple as possible (WYSIWYG Editor) all while providing as many features as possible
- Prioritize security and confidentiality

🚀 Features
--------
- Modern GUI
- Complete Markdown language support
- Support for font size as well as font styles
- Lightweight (executable size ~10MB)
- Full compatibility with any other text editor

🖼️ Screenshot
----------
![SuperCoolMarkDownGIF](doc/GUI.gif)

💾 Download
--------
Pre-built executables for Windows, macOS, and Linux are found on the [Releases](https://github.zhaw.ch/PM2-IT20taWIN-bles-mach-kars/hk2-team2-itsafeature-project2-SuperCoolMarkdownEditor/releases) page.

⚙️ Build from source
-----------------
To build SuperCoolMarkDown, you need the following:

* [Gradle] - v6.8+
* [Java] - v11+
-------------

1. You need to download Gradle and a Java JDK manually if you don't have them installed already. All other dependencies will be downloaded automatically after loading the gradle project.
1. To generate an executable, you first need to generate a jar file. That's as simple as running
```sh
gradle build
```
You'll find your jar file in `build/libs/SuperCoolMarkDown-xx.jar`. To execute it, run 
```sh
java -jar SuperCoolMarkDown-xx.jar
```
3. To package the jar file in an executable use [launch4j][]. You can use `launch4j.conf.xml` to load the config. Make sure to update the path to the jar file, press the gear icon to create the executable, and voilà!

To clean everything run `gradle clean` or simply delete `build/`.

ℹ️ Additional information for nerds
-----------
The branching model for this project is the following:
* The default branch is the master branch
* Hotfixes may be pushed directly to master but in any case, please talk to a maintainer first (you can open an issue for that)
* When merging branches to master, a PR must be created and reviewed by a maintainer first
* Every feature or bug that is not trivial has its own issue
* Every issue has its own branch that should be deleted when the issue is resolved
* Commit messages should refer to a specific issue with #<issue_number>
______________
The class diagram is provided [here](https://github.zhaw.ch/PM2-IT20taWIN-bles-mach-kars/hk2-team2-itsafeature-project2-SuperCoolMarkdownEditor/blob/master/doc/classdiagram.png) and the architecture documentation can be found [here](https://github.zhaw.ch/PM2-IT20taWIN-bles-mach-kars/hk2-team2-itsafeature-project2-SuperCoolMarkdownEditor/blob/master/doc/architecture_documentation.md). The test documentation is available [here](https://github.zhaw.ch/PM2-IT20taWIN-bles-mach-kars/hk2-team2-itsafeature-project2-SuperCoolMarkdownEditor/tree/master/doc/TestDocumentation).
_________________
Examples for good pull requests:
1. [Add fxml file for editor window](https://github.zhaw.ch/PM2-IT20taWIN-bles-mach-kars/hk2-team2-itsafeature-project2-SuperCoolMarkdownEditor/pull/19)
1. [Logo](https://github.zhaw.ch/PM2-IT20taWIN-bles-mach-kars/hk2-team2-itsafeature-project2-SuperCoolMarkdownEditor/pull/42)

❤️ Feedback
--------
Found a bug? Got suggestions? [Create an issue](https://github.zhaw.ch/PM2-IT20taWIN-bles-mach-kars/hk2-team2-itsafeature-project2-SuperCoolMarkdownEditor/issues/new/choose)!

📝 Legal
--------
This project has been published under the GPL-3 license

Additional information can be found here: [GNU.org](https://www.gnu.org/licenses/gpl-3.0.en.html)

🧑‍🤝‍🧑 Our awesome team
--------
- Silvan Baach @baachsil (Project manager)
- Jon Defilla @defiljon (Dev and Maintainer)
- Badr Outiti @outitbad (Dev and Maintainer)
- Julian Peter @peterju1 (Dev and Maintainer)

⏱ Time worked on this project
--------
|Date   | Badr Outiti  | Jon Defilla | Julian Peter | Silvan Baach | Total |
|---|---|---|---|---|---|
|19.04. - 25.04.   | 3h 32min  | 4h 39min   | 3h 32min    | 6h 28min   | 18h 11min |
|26.04. - 02.05.   | 2h 38min   | 0min   | 4h 06min| 4h 35min | 11h 19min |
|03.05. - 09.05.   | 12h 06min  | 5h 09min  | 10h 00min | 8h 20min | 35h 35min |
|10.05. - 14.05.   | 15h 13min  | 20h 00min  | 15h 02min | 17h 18min | 67h 33min |
| Total:   | 33h 29min  | 29h 48min   | 32h 41min | 36h 41min | **132h 39min** |


[Gradle]: https://gradle.org/install/
[Swing]: https://wikipedia.org/wiki/Swing_(Java)
[Java fx]: https://openjfx.io/
[Java]: https://www.oracle.com/java/technologies/javase-jdk11-downloads.html
[launch4j]: http://launch4j.sourceforge.net/
