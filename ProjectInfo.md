### Introduction ###

gprof-eclipse is a plugin for Eclipse that will allow the profiling capabilities of gprof to be used alongside Eclipse and the CDT.


### Status ###

gprof-eclipse currently can launch a project that has already been run and has generated the gmon.out file. The output is correctly parsed and stored in an internal model.

### Next Steps ###

Here are the things that need to be done next:
  * Implement a UI-ideally the internal model would be adapted to work with TPTP and then the TPTP UI would be used to display the profiling results (and provide links back to the code, etc.).
  * Upgrade the launch plugin to be derived from the CDT launch plugin and allow for any Run/Debug CDT session to be launched as usual and then have gprof-eclipse parse and display the profiling results afterwards.