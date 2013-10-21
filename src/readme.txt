cse13195, 210352698, Luna, Dominique
cse13181, 207937469, Anderson, Devon
cse13188, 211704558, Lis, Maciej        

Also I think we need to change the name of KioskMain.java to a1.java
to make them happy

If you want to change stuff in here or add/modify things go ahead I just
wrote what came to me.

Meetings

    Wednesday October 16th
        - Designated roles
        - Started talking about the design for the kiosk parking

    Roles Assigned

        Devon: Reading/Writing DB files to interact with main app
        Maciej: Most experienced with GUI programming, design of the main program
        Dominique: Receipt Permit design, help out Maciej with main design


    We didn't have any further in person meetings with all 3 of us. We further communicated
    through email and used Github to collaborate and for version control. 


Design Philosophies
          
    Software

    In terms of the software engineering portion we choose Github to collaborate. The reasons
    for this being:

        1)  Very easy to maintain version control, we don't lose changes or can revert changes
            easily

        2)  Super easy to collaborate without being in the same physical location or being really
            co-ordinated with transfering files.

        3)  Easy to see what changes are made to which files.

    We also decided to make the application modular. The reasons for this being:

        1)  With one big file it's very hard to reason about which parts do what. Seperating
            the logic into multiple files and classes allows this reasoning to be far easier.

        2)  Making it modular allows means we don't have to break everything if we want to change something.
            We just change the file that logic is contained in.

        3)  Makes it easy to assign roles to people in the group and not duplicate code.


    GUI Design
    
        1)  we chose to go with a flat UI for the overall look and feel of the application, something that's
            really starting to pick up trend - when people look at a screen, especially one without a keyboard they 
            intuitively think it's responsive to touch. So we felt this was a safe, and stylish approach. 
            
        2)  instead of presenting the user with one huge form to fill out, we felt breaking it up into peices would 
            ease the initial burden of being presented a massive form. Something none of us like to fill out.
            If the user feels the form is to long, they may click cancel at any point to clear
            progress and return to the initial login screen. 
        
        3)  the application supports multiple window sizes, but is intended to be run as full screen. 
        

