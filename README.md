# chatGPT-solution-generator-intellij-plugin
A plugin generates solution for programming tasks using ChatGPT right in the IntelliJ IDEA.

## Usage
1. First you must enter your Open AI API token in __Preferences | Tools | ChatGPT Solution Generator__ and click __Apply__. To get your API token follow [this page](https://elephas.app/blog/how-to-create-openai-api-keys-cl5c4f21d281431po7k8fgyol0)
<img src="/images/1.png" alt="Preferences" width="70%"/>

2. Then you need to open any file in your idea, write there your task statement, highlight it and select **Tools | Chat GPT Solution generator** or press `^+\` and `^+G` (on MacOs)
<img src="/images/2.png" alt="Preferences" width="70%"/>

3. Then you got the answer from Chat GPT
<img src="/images/3.png" alt="Preferences" width="70%"/>


## Features
### Autodetecting the programming language used
The plugin detects which programming language you are currently using (by your file extension) and asks Open AI to respond in this programming language. Thus, you don't need to write in the problem statement what programming language must be used:
+ C++:

<img src="/images/4.png" alt="Preferences" width="45%"/>  <img src="/images/5.png" alt="Preferences" width="45%"/>

+ Kotlin:

