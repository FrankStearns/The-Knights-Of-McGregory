Frank Stearns - Project 3

You are the last surviving knight of the castle of McGregory. The castle is under
siege by monsters of all shapes and sizes. Can you traverse the halls and slay
the foul beasts, or will you be struck down like the rest of your fallen
bretheren. There is only one way to find out...

I added a lot of features but I'll do my best to explain them all here. 

1. Added gravity and jumping mechanics inluding a double jump. The character has a jumping
animation and a falling animation and some extra physics when interaticing with objects. 

2. Added a sword attack with an animation that increases the players hitbox so the sword
can hit enemies. The attack works when jumping and when on top of objects. There is also 
an attack cool down to prevent spamming attacks.

3. Added three new types of enemies with unique patterns, assets, and sizes. They do 
damage to the player if the player touches them while not attacking. Each enemy drops 
a coin that can be collected when killed by the player.

4. Added a scrolling animated background to look like the character is moving 
down the hallway.

5. Added hearts to the top of the screen that show the players health and change to empty
when the player is hit. Also added stacking coins to show the player how many coins they 
have.

6. Added a new splash screen that explains controls and rules.

7. Added a death screen and a win screen when the conditions are met.

8. Added scrolling enviromental objects that have full physics and interaction with the 
player. The player can stand on top of them, bump its head on them, and will be pushed 
down the hall by it if you dont maneuver around them properly. If you are pushed out of 
bounds, the player will take damage off screen.

9. Added a period of invulnerability if the player takes damage. The player flashes to
let them know they can not take damage for a little bit. This also stops the objects 
from pushing the player off screen by automatically putting the player on top of the 
objects so they dont take any more damage.

10. Added several new animations including a death animation, enemy animations, and 
even an idle pause animation for you character

11. Added a few other features and debugs but this file is getting too long.

The Win and Lose conditions are:

The game is won when you have killed and collected the coins from 8 monsters, a win screen
will appear.

The game is lost when you take three damage from either monsters or being pushed off screen.
"You died" will appear on the screen and your character will fall over, throw their sword
and die. 

I really hope you enjoy the game, I spent way way too long on this. I spent a long time 
optimizing the game play and I think it is really fun. The movement is my 
favorite part. I'm particularly proud of the way I created jumping.

Thank you Professor!

Sources:

Background image = https://pixeljoint.com/pixelart/111480.htm

Player Character art = https://www.gamedevmarket.net/asset/animated-knight-character-pack

Splash Screen: https://www.freepik.com/free-ai-image/8-bit-graphics-pixels-scene-with-castle_137494400.htm#query=pixel%20castle&position=35&from_view=keyword&track=ais&uuid=f926326e-721b-4a20-b139-85d1311878d0

Death Screen: https://www.google.com/search?q=you+died+no+background&sca_esv=7b7e7809a2184a8f&sca_upv=1&ei=CagqZu7ZLIWIptQP8L6VyAQ&oq=you+died+no+back&gs_lp=Egxnd3Mtd2l6LXNlcnAiEHlvdSBkaWVkIG5vIGJhY2sqAggAMgUQIRigATIFECEYoAEyBRAhGKABMgUQIRigATIFECEYnwUyBRAhGJ8FSIsPUKkBWMoIcAB4ApABAJgB3gOgAbASqgEHMi00LjEuMrgBAcgBAPgBAZgCCKACwhLCAgQQABhHwgIFEAAYgATCAgUQLhiABMICChAuGIAEGEMYigXCAgoQABiABBhDGIoFwgIGEAAYFhgewgILEAAYgAQYhgMYigWYAwCIBgGQBgiSBwkxLjAuNC4xLjKgB9Uu&sclient=gws-wiz-serp#vhid=8PMIuhfhHQ3zpM&vssid=l

Coin: https://i.pinimg.com/originals/1c/cb/70/1ccb701d052028aa79f41e135006451d.png

Skeleton: https://cdn.dribbble.com/users/114039/screenshots/6684278/skeleton_sprite_dribbble.gif

Bat: https://www.google.com/search?sca_esv=5fa93554276c39d0&sca_upv=1&q=bat+pixel+gif&uds=AMwkrPuyqEkls4Ckdf3Xn72nW7ALKXmVsch-X_1HFuKMtdFWDUxgXfyiKE54cckcRIxm3QNQiNaBEJvagsPT2gLLawybuVoNp8Q-H7Q2DlEdOeaeZ5HHWDCY0U3onXq7sL-oIMW_zikalV3PkmkVvibohJogByzlUElN_4Xt2czVGa8wZQMH6Gj-owh2KJC35Yb8muKFurnhG5ME70Ft-Sf4GYNcm8OiQwqrsnrTcGhSY95UcWpTmVRycvem7rtYwVGyBXJPvbQaPYo58w9rpPr_jUuLAjlJsGAVnWr99mvLbrvxqtptpIJ7RgS7EvlFUvCOa08TUPF9&udm=2&prmd=ivsnbmtz&sa=X&ved=2ahUKEwji8-Gl7N6FAxWxFlkFHa7wAH8QtKgLegQIERAB&biw=1438&bih=730&dpr=2#vhid=sm666AuW-_X55M&vssid=mosaic

Win Screen: https://www.google.com/search?q=castle+gif&sca_esv=9a46615aa720b818&sca_upv=1&udm=2&biw=1438&bih=730&ei=xgAsZuHyIZGo5NoPp6-HmAM&ved=0ahUKEwjh7db2zuCFAxURFFkFHafXATMQ4dUDCBA&uact=5&oq=castle+gif&gs_lp=Egxnd3Mtd2l6LXNlcnAaAhgDIgpjYXN0bGUgZ2lmMggQABiABBiLAzIIEAAYgAQYiwMyCBAAGIAEGIsDMggQABiABBiLAzIIEAAYgAQYiwMyCBAAGIAEGIsDMggQABiABBiLAzIIEAAYgAQYiwMyCBAAGIAEGIsDMggQABiABBiLA0itFlAAWMoVcAR4AJABAJgBZqABzQeqAQQxMS4xuAEDyAEA-AEBmAIQoAKPCMICChAAGIAEGEMYigXCAg0QABiABBhDGIoFGIsDwgILEAAYgAQYsQMYiwPCAhEQABiABBixAxiDARiKBRiLA8ICEBAAGIAEGLEDGEMYigUYiwPCAgQQABgDwgIHEAAYAxiLA8ICCBAAGIAEGLEDwgIOEAAYgAQYsQMYgwEYiwPCAgUQABiABJgDAJIHBDE1LjGgB8JB&sclient=gws-wiz-serp#vhid=iO6EsL68F0OkoM&vssid=mosaic