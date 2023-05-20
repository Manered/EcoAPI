# EcoAPI
Simple EconomyAPI plugin you can integrate in your own plugins.

# Adding EcoAPI to your Project

## 1. Maven:
Step 1. Add the JitPack repository to your build file:
```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
Step 2. Add the dependency
```xml
	<dependency>
	    <groupId>com.github.Manered</groupId>
	    <artifactId>EcoAPI</artifactId>
	    <version>Tag</version>
	</dependency>
```

## 2. Gradle:
Step 1. Add it in your root build.gradle at the end of repositories:
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```gradle
	dependencies {
	        implementation 'com.github.Manered:EcoAPI:Tag'
	}
```
