steganography
=============

A little toy project to embed ascii messages in png files. 
Works without error so far but there is no check whether the message is actually too long for the image.

Any suggestions how to improve the code are welcome, it looks like programmed with a hammer to me. 

usage: Steganography.embed(MESSAGE_STRING, ORIGINAL_FILENAME, NEW_FILENAME)

       Steganography.extract(NEW_FILENAME)
       
Images have to be png files, messages should be ascii encoded.
