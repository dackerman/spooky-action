A web application to allow for asynchronous editing of files on the local file system of the target machine running the server


### TODO

1. Add assembly plugin for maven so the whole webapp can be deployed as a single executable jar.
1. Move lines with Alt+Up and Alt+Down
1. Show suggestions as-you-type for a file
1. Before sending list of commands over the wire, compress them.
1. Cut/Copy/Paste functionality
1. Add saving via Ctrl+S
1. Add periodic saving
1. Add syntax highlighting to the editor


### BUGS

* None currently


### FIXED BUGS

* **[FIXED]** Make hitting <spacebar> not scroll the screen


### POSSIBLE REFACTORING
* Clean up Editor.java - it seems to be doing too much (RPC, handlers, editor manip).
* Find a better place to put all the editor logic for moving the cursor between lines, and when to stopPropagation.


### DONE

* <del>Show last command</del>
* <del>Show error and info messages at the top</del>
* <del>Show error when user doesn't type the right file</del>
* <del>When a list of commands gets sent to the backend, update the file appropriately.</del>
* <del>Show carat in Editor area</del>
* <del>Show number of commands in the queue, so it's visible when the queue is drained.</del>
* <del>When user clicks save, send list of commands to backend</del>
* <del>Show status of saving</del>
* <del>Make carat blink</del>
