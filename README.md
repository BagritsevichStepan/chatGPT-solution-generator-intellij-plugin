# chatGPT-solution-generator-intellij-plugin
A plugin generates solution for programming tasks using ChatGPT right in the IntelliJ IDEA.

+ [Usage](#usage)
+ [Features](#features)
  1. [Detecting the programming language](#detecting-the-programming-language)
  2. [Markdown parser](#markdown-parser)
  3. [Comments adding](#comments-adding)

## Usage
1. First you must enter your Open AI API token in __Preferences | Tools | ChatGPT Solution Generator__ and click __Apply__. To get your API token follow [this page](https://elephas.app/blog/how-to-create-openai-api-keys-cl5c4f21d281431po7k8fgyol0)
<img src="/images/1.png" alt="Preferences" width="90%"/>

2. Then you need to open any file in your idea, write there your task statement, highlight it and select **Tools | Generate Solution** or press `^+\` and `^+G` (on MacOs)
<img src="/images/2.png" alt="Preferences" width="90%"/>

3. Then you got the answer from Chat GPT right in the Editor
<img src="/images/3.png" alt="Preferences" width="90%"/>


## Features
### Detecting the programming language
The plugin detects which programming language you are currently using (by your file extension) and asks Open AI to respond in this programming language. Thus, you don't need to write in the problem statement what programming language must be used:
+ **C++:**

<img src="/images/4.2.png" alt="Preferences" width="100%"/>  <img src="/images/5.2.png" alt="Preferences" width="100%"/>

+ **Kotlin:**

<img src="/images/6.png" alt="Preferences" width="45%"/>  <img src="/images/7.png" alt="Preferences" width="45%"/>

### Markdown parser
The plugin gets a response from Chat GPT in Markdown. [My own library](https://github.com/BagritsevichStepan/java-course/tree/main/MarkdownToHtml) parses the server response and inserts the response into the editor.

### Comments adding
Аfter the response is processed by the Markdown-Parser, the plugin adds comments to the response lines that are not part of the code blocks. The plugin supports the comment syntax of many programming languages.

+ **HTML:**

<img src="/images/8.png" alt="Preferences" width="45%"/>  <img src="/images/9.png" alt="Preferences" width="45%"/>
