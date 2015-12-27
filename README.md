steganography
=============

A little toy project to embed ascii messages in png files. 
If the message is too long for the image, it will be cut off without warning.

The code is free to be used by anyone who is interested, but comes of course with no warranty nor a suggestion to use it for anything important or valuable.

usage: 
       Steganography embed "message to embed" original_file.png new_file.png
       Steganography extract new_file.png
       
Images have to be png files, messages can have special characters (I use 16 bit encoding)
