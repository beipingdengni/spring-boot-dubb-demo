



```groovy

sourceSets {
   main {
      java {
         srcDir 'java-sources'
      }
      resources {
         srcDir 'resources'
      }
   }
}

//获取 common.gradle 依赖插件配置
buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath 'net.researchgate:gradle-release:2.4.0' // release plugin
    }
}

// 1 所有模块配置
allprojects {
    apply from: "${rootProject.projectDir}/common.gradle"
}

// 2、修改 web 模块，web/build.gradle 增加配置：
apply from: "${rootProject.projectDir}/common.gradle"

```

#### clean 本地包
```text

task deleteDescriptors(type: Exec) { //执行shell命令
    executable "sh"
    args "-c", "rm -rf ~/.gradle/caches/modules-2/metadata-2.16/descriptors/com.company.appname" 
    //此处的“com.company.appname“就是之前定义的“repositoryGroup“。
}

task clean(type: Delete, dependsOn: deleteDescriptors) { //clean工程时顺带执行上述任务
    delete rootProject.buildDir
}

```


#### 执行命令
```text
gradle release // 执行所有模块
gradle :release // 只执行父模块
gradle web:release // 只执行 web 子模块
gradle web:release api:release //只执行 web、api子模块


//插件使用
apply plugin: 'application'

group = "com.gradle.api"
version = "${rootProject.version}" //也可以不写，默认继承父模块 version
description = "this is gradle api demo"
archivesBaseName = 'gradle_api'

mainClassName = "com.gradle.api.Main" //指定 Jar 启动 Main

```


#### upload  jar 
```text

ext {
    civersion = System.getProperty("ci-version") ?: "${project.version}"
    maven_username = System.env.maven_username
    maven_password = System.env.maven_password
    
    
    System.getProperty("maven_username") //  gradle uploadArchives -Dmaven_username=admin
    System.env.maven_username    //   gradle uploadArchives  需要机器中配置 [ export maven_username=admin ]
}


uploadArchives {
    repositories {
        mavenDeployer {
//            pom.version = "${civersion}"
//            pom.artifactId = "${project.archivesBaseName}"
//            pom.groupId = "${project.group}"
            if (mavenUpload){
                pom.version = "${project.version}".replaceAll('SNAPSHOT','RELEASE')
                repository(url: 'http://localhost:8081/repository/maven-releases/') {
                    authentication(userName: 'admin', password: '123456')
//                    releases(enabled:mavenUpload,updatePolicy:'interval:60',checksumPolicy:'warn')
                }
            }else {
                snapshotRepository(url: 'http://localhost:8081/repository/maven-snapshots/') {
                    authentication(userName: 'admin', password: '123456')
//                    snapshots(enabled:!mavenUpload,updatePolicy:'always',checksumPolicy:'warn')
                }
            }
        }
    }
}



#### gradle-release 插件

// https://www.ctolib.com/mip/gradle-release.html

plugins {
    id 'net.researchgate.release' version '2.6.0'
}



// config release task
release {
    tagCommitMessage = "[Gradle Release Plugin] - creating tag: "
    scmAdapters = [
            net.researchgate.release.GitAdapter,
    ]
}

```