package fi.dy.masa.litematica;

import fi.dy.masa.litematica.event.*;
import fi.dy.masa.litematica.network.PacketListenerRegister;
import fi.dy.masa.malilib.event.*;
import net.minecraft.client.MinecraftClient;

import fi.dy.masa.litematica.config.Configs;
import fi.dy.masa.litematica.data.DataManager;
import fi.dy.masa.litematica.render.infohud.StatusInfoRenderer;
import fi.dy.masa.litematica.scheduler.ClientTickHandler;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import fi.dy.masa.malilib.interfaces.IRenderer;

public class InitHandler implements IInitializationHandler
{
    @Override
    public void registerModHandlers()
    {
        ConfigManager.getInstance().registerConfigHandler(Reference.MOD_ID, new Configs());

        InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.getInstance());
        InputEventHandler.getInputManager().registerKeyboardInputHandler(InputHandler.getInstance());
        InputEventHandler.getInputManager().registerMouseInputHandler(InputHandler.getInstance());

        IRenderer renderer = new RenderHandler();
        RenderEventHandler.getInstance().registerGameOverlayRenderer(renderer);
        RenderEventHandler.getInstance().registerWorldLastRenderer(renderer);

        TickHandler.getInstance().registerClientTickHandler(new ClientTickHandler());

        WorldLoadListener listener = new WorldLoadListener();
        WorldLoadHandler.getInstance().registerWorldLoadPreHandler(listener);
        WorldLoadHandler.getInstance().registerWorldLoadPostHandler(listener);

        KeyCallbacks.init(MinecraftClient.getInstance());
        StatusInfoRenderer.init();

        PacketListenerRegister.registerListeners();

        DataManager.getAreaSelectionsBaseDirectory();
        DataManager.getSchematicsBaseDirectory();
    }
}
