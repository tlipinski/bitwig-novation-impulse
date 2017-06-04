package net.tlipinski;

import com.bitwig.extension.api.PlatformType;
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList;
import com.bitwig.extension.controller.ControllerExtensionDefinition;
import com.bitwig.extension.controller.api.ControllerHost;

import java.util.UUID;

public class NovationImpulse4961ExtensionDefinition extends ControllerExtensionDefinition
{
   private static final UUID DRIVER_ID = UUID.fromString("c502ace6-b694-4037-a924-4476f9062b0f");
   
   public NovationImpulse4961ExtensionDefinition()
   {
   }

   @Override
   public String getName()
   {
      return "Novation Impulse 49/61";
   }
   
   @Override
   public String getAuthor()
   {
      return "pplcanfly";
   }

   @Override
   public String getVersion() {
       return Version.get();
   }

   @Override
   public UUID getId()
   {
      return DRIVER_ID;
   }
   
   @Override
   public String getHardwareVendor()
   {
      return "Novation";
   }
   
   @Override
   public String getHardwareModel()
   {
      return "Impulse 49/61";
   }

   @Override
   public int getRequiredAPIVersion()
   {
      return 3;
   }

   @Override
   public int getNumMidiInPorts()
   {
      return 2;
   }

   @Override
   public int getNumMidiOutPorts()
   {
      return 1;
   }

   @Override
   public void listAutoDetectionMidiPortNames(final AutoDetectionMidiPortNamesList list, final PlatformType platformType)
   {
      if (platformType == PlatformType.WINDOWS) {
         // TODO: Set the correct names of the ports for auto detection on Windows platform here
         // and uncomment this when port names are correct.
         // list.add(new String[]{"Input Port 0", "Input Port 1"}, new String[]{"Output Port 0", "Output Port 1"});
      }
      else if (platformType == PlatformType.MAC) {
         String[] inputPortNames = { "Impulse  Impulse ", "Impulse  Impulse MIDI In " };
         String[] outputPortNames = { "Impulse  Impulse " };
         list.add(inputPortNames, outputPortNames);
      }
      else if (platformType == PlatformType.LINUX) {
          String[] inputPortNames = { "Impulse MIDI 1", "Impulse MIDI 2" };
          String[] outputPortNames = { "Impulse MIDI 1" };
          list.add(inputPortNames, outputPortNames);
      }
   }

   @Override
   public NovationImpulse4961Extension createInstance(final ControllerHost host)
   {
      return new NovationImpulse4961Extension(this, host);
   }
}
