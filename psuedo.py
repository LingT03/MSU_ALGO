def matchOrientation():

   # Define positions
inside = 0
outside = 1

# Define tubes in axes
axis1 = {
    "Left_tube": inside,
    "Right_tube": inside
}

axis2 = {
    "Top_tube": inside,
    "Bottom_tube": inside
}

# Define states
states = {
    "state1": {
        "Left_tube": inside,
        "Right_tube": inside,
        "Top_tube": inside,
        "Bottom_tube": inside
    },
    "state2": {
        "Left_tube": outside,
        "Right_tube": outside,
        "Top_tube": outside,
        "Bottom_tube": outside
    },
    "state3": {
        "Left_tube": inside,
        "Right_tube": inside,
        "Top_tube": outside,
        "Bottom_tube": outside
    },
    "state4": {
        "Left_tube": inside,
        "Right_tube": outside,
        "Top_tube": inside,
        "Bottom_tube": inside
    },
    "state5": {
        "Left_tube": inside,
        "Right_tube": outside,
        "Top_tube": outside,
        "Bottom_tube": outside
    },
    "state6": {
        "Left_tube": inside,
        "Right_tube": outside,
        "Top_tube": inside,
        "Bottom_tube": outside
    }
}



    while unsolved:
        #assume the centrifuge is spinning
        STOP()
        left_tube = REPORT_LEFT()
        right_tube = REPORT_RIGHT()

        # store in axis1
        if left_tube == "inside":
            axismap.append("Left_tube")
        else:
            axismap.append("Right_tube")
        
"""
NCED Reports whether or not the centrifuge tray is balanced (all tubes have
the same orientation); results are valid only while the centrifuge is
spinning
FLIP_LEFT Flips the orientation of the tube exposed by the LEFT door (if and only
if the LEFT door is open; otherwise shuts down)
FLIP_RIGHT Flips the orientation of the tube exposed by the RIGHT door (if and
only if the RIGHT door is open; otherwise shuts down)
FLIP_TOP Flips the orientation of the tube exposed by the TOP door (if and only
if the TOP door is open; otherwise shuts down)
OPEN_RIGHT Opens the RIGHT door (if and only if the LEFT door is open and the
TOP door is closed; otherwise shuts down)
_TOP Opens the TOP door (if and only if the LEFT door is open and the
RIGHT door is closed; otherwise shuts down)
REPORT_LEFT Reports the orientation of the tube under the LEFT door (if and only if
the LEFT door is open; otherwise shuts down)
REPORT_RIGHT Reports the orientation of the tube under the RIGHT door (if and only if
the RIGHT door is open; otherwise shuts down)
REPORT_TOP Reports the orientation of the tube under the TOP door (if and only if
the TOP door is open; otherwise shuts down)
SPIN Closes all open doors and starts the centrifuge spinning (if the
centrifuge is already spinning, this command is ignored)
STOP Applies brake to the centrifuge and automatically opens the LEFT
door when the centrifuge has come to a complete stop (if the
centrifuge is already stopped, this command is ignored)
"""