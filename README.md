# ChatGPT Solution Generator
The plugin generates solution for programming tasks using ChatGPT right in the IntelliJ IDEA.

+ [Usage](#usage)
+ [Features](#features)
  + [Detecting the programming language](#detecting-the-programming-language)
  + [Splitting into lines](#splitting-into-lines)
  + [Comments adding](#comments-adding)

## Usage
1. First you enter your Open AI API token in __Preferences | Tools | ChatGPT Solution Generator__ and click __Apply__. To get your API token follow [this page](https://elephas.app/blog/how-to-create-openai-api-keys-cl5c4f21d281431po7k8fgyol0)
<img src="/images/1.png" alt="Preferences" width="90%"/>

2. Then you need to open any file in your IDEA, write there your task statement, highlight it and select **Tools | Generate Solution** or press `^+\` and `^+G` (on MacOs)
<img src="/images/2.png" alt="Request" width="90%"/>

3. Then you got the answer from Chat GPT right in the Editor
<img src="/images/3.png" alt="Response" width="90%"/>


## Features
### Detecting the programming language
The plugin detects which programming language you are currently using (by your file extension) and asks Open AI to respond in this programming language. Thus, you don't need to write in the problem statement what programming language should be used:
+ **C++:**

![C++ request](/images/4.2.png)
![C++ response](/images/5.2.png)

+ **Kotlin:**

![Kotlin request](/images/6.2.png)
![Kotlin response](/images/7.2.png)

### Splitting into lines
Аfter the response is processed by the Markdown-Parser, the plugin divides the response into lines according to the width of the user's screen. So that horizontal scrolling does not occur. The plugin does not separate lines that are part of the code blocks, so that the code stays correct. Also word transposition is taken into account. 

### Comments adding
Аfter splitting the plugin adds comments to the response lines that are not part of the code blocks. The plugin supports the comments syntax of many programming languages.

+ **HTML:**

![HTML request](/images/8.2.png)
![HTML response](/images/9.2.png)
