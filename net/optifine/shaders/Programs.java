package net.optifine.shaders;

import java.util.ArrayList;
import java.util.List;

public class Programs {
   private List<net.optifine.shaders.Program> programs = new ArrayList();
   private net.optifine.shaders.Program programNone = this.make("", ProgramStage.NONE, true);

   public net.optifine.shaders.Program make(String name, ProgramStage programStage, net.optifine.shaders.Program backupProgram) {
      int index = this.programs.size();
      net.optifine.shaders.Program prog = new net.optifine.shaders.Program(index, name, programStage, backupProgram);
      this.programs.add(prog);
      return prog;
   }

   private net.optifine.shaders.Program make(String name, ProgramStage programStage, boolean ownBackup) {
      int index = this.programs.size();
      net.optifine.shaders.Program prog = new net.optifine.shaders.Program(index, name, programStage, ownBackup);
      this.programs.add(prog);
      return prog;
   }

   public net.optifine.shaders.Program makeGbuffers(String name, net.optifine.shaders.Program backupProgram) {
      return this.make(name, ProgramStage.GBUFFERS, backupProgram);
   }

   public net.optifine.shaders.Program makeComposite(String name) {
      return this.make(name, ProgramStage.COMPOSITE, this.programNone);
   }

   public net.optifine.shaders.Program makeDeferred(String name) {
      return this.make(name, ProgramStage.DEFERRED, this.programNone);
   }

   public net.optifine.shaders.Program makeShadow(String name, net.optifine.shaders.Program backupProgram) {
      return this.make(name, ProgramStage.SHADOW, backupProgram);
   }

   public net.optifine.shaders.Program makeVirtual(String name) {
      return this.make(name, ProgramStage.NONE, true);
   }

   public net.optifine.shaders.Program[] makePrograms(String prefix, int count, ProgramStage stage, net.optifine.shaders.Program backupProgram) {
      net.optifine.shaders.Program[] ps = new net.optifine.shaders.Program[count];

      for (int i = 0; i < count; i++) {
         String name = i == 0 ? prefix : prefix + i;
         ps[i] = this.make(name, stage, this.programNone);
      }

      return ps;
   }

   public net.optifine.shaders.Program[] makeComposites(String prefix, int count) {
      return this.makePrograms(prefix, count, ProgramStage.COMPOSITE, this.programNone);
   }

   public net.optifine.shaders.Program[] makeShadowcomps(String prefix, int count) {
      return this.makePrograms(prefix, count, ProgramStage.SHADOWCOMP, this.programNone);
   }

   public net.optifine.shaders.Program[] makePrepares(String prefix, int count) {
      return this.makePrograms(prefix, count, ProgramStage.PREPARE, this.programNone);
   }

   public net.optifine.shaders.Program[] makeDeferreds(String prefix, int count) {
      return this.makePrograms(prefix, count, ProgramStage.DEFERRED, this.programNone);
   }

   public net.optifine.shaders.Program getProgramNone() {
      return this.programNone;
   }

   public int getCount() {
      return this.programs.size();
   }

   public net.optifine.shaders.Program getProgram(String name) {
      if (name == null) {
         return null;
      } else {
         for (int i = 0; i < this.programs.size(); i++) {
            net.optifine.shaders.Program p = (net.optifine.shaders.Program)this.programs.get(i);
            String progName = p.getName();
            if (progName.equals(name)) {
               return p;
            }
         }

         return null;
      }
   }

   public String[] getProgramNames() {
      String[] names = new String[this.programs.size()];

      for (int i = 0; i < names.length; i++) {
         names[i] = ((net.optifine.shaders.Program)this.programs.get(i)).getName();
      }

      return names;
   }

   public net.optifine.shaders.Program[] getPrograms() {
      return (net.optifine.shaders.Program[])this.programs.toArray(new net.optifine.shaders.Program[this.programs.size()]);
   }

   public net.optifine.shaders.Program[] getPrograms(net.optifine.shaders.Program programFrom, net.optifine.shaders.Program programTo) {
      int iFrom = programFrom.getIndex();
      int iTo = programTo.getIndex();
      if (iFrom > iTo) {
         int i = iFrom;
         iFrom = iTo;
         iTo = i;
      }

      net.optifine.shaders.Program[] progs = new net.optifine.shaders.Program[iTo - iFrom + 1];

      for (int i = 0; i < progs.length; i++) {
         progs[i] = (net.optifine.shaders.Program)this.programs.get(iFrom + i);
      }

      return progs;
   }

   public String toString() {
      return this.programs.toString();
   }
}
