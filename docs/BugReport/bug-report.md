---
layout: default
title: Bug Report
nav_order: 7
permalink: /BugReport
search_exclude: true
---

# Navigation Structure
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Bug Report

![bug-enemy.gif](../assets/images/bug-enemy.gif)

Below is a list of known bugs and oddities in the game code.
There are definitely more bugs than what is listed here...they just haven't been discovered yet.

## Options Menu cannot change aspect ratio

During this Project Our team created an options menu with the hopes to adjust the volume and the Aspect Ratio or the size of the game.
After attempting to implement the aspect ratio adjuster into the game we soon realized that to change the size of the game window we would also 
have to change the size of every object in the game. Because of this the aspect ration option was left unfinished.
Our team sees this as a bug or an unfinished improvment and would love to see it finished by another team.

## Narrative Screen only lasts 15 seconds

On the main menu there is a option for narrative this will take you to a narrative screen but only for 15 seconds onece the timer
runs out it will return you to the main menu.
To fix this bug we recommend making the narrative screen and actual screen object and placing a back button on it
so the player can say on this screen as long as they want.

## White color rgb(255,255,255) is transparent

I am not quite sure why this is, but if an image uses the color white with the rgb (255,255,255), the color will be
transparent when loaded in. I think it has to do with the way the `transformColorToTransparency` method works (which I lifted
from a StackOverflow answer) that I use in order to set a transparent color in an image when loading it in (the default transparent color
is magenta rgb (255,0,255)). Maybe there's a better transparency method out there that doesn't have this adverse effect on the color white?

The good news is this is super easy to work around -- just use a white color with a different rgb in your images, such as (255,255,254) (which will look
no different in game than (255,255,255) I promise). Off the top of my head, the `Walrus.png` image uses this technique for its tusks because
pure white wouldn't show, which is how I found out about this bug.
