---
layout: default
title: Narrative Screen
nav_order: 7
parent: Screens
grand_parent: Game Code Details
permalink: /GameCodeDetails/Screens/NarrativeScreen
---

# Navigation Structure
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Narrative Screen

The screen handles the logic and graphics related to the screen that pops up when narrative is selected.

![narrative-screen.PNG](../../../assets/images/narrative-screen.PNG)

The class file for it is `OpeningScreen.java` which can be found in the `Screens` package.

## Functionality

The narrative screen's only real job is to allow the player to see the story for why the game is happening.

## Graphics

The background of the narrative screen uses a `Map` specifically made for it in the `Maps` package, which is the same type of `Map` class which
is used when actually playing the platformer game. While any image could have been used, I thought it'd be more fun to use a map as the background.
