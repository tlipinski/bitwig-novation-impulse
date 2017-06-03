package net.tlipinski;

import com.bitwig.extension.api.PlatformType;
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList;
import com.bitwig.extension.controller.ControllerExtensionDefinition;
import com.bitwig.extension.controller.api.ControllerHost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class NovationImpulse49ExtensionDefinition extends ControllerExtensionDefinition
{
   private static final UUID DRIVER_ID = UUID.fromString("c502ace6-b694-4037-a924-4476f9062b0f");
   
   public NovationImpulse49ExtensionDefinition()
   {
   }

   @Override
   public String getName()
   {
      return "Novation Impulse 49";
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
      return "Impulse 49";
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
         // TODO: Set the correct names of the ports for auto detection on Linux platform here
         // and uncomment this when port names are correct.
         // list.add(new String[]{"Input Port 0", "Input Port 1"}, new String[]{"Output Port 0", "Output Port 1"});
      }
   }

   @Override
   public NovationImpulse49Extension createInstance(final ControllerHost host)
   {
      return new NovationImpulse49Extension(this, host);
   }
}
