/*
 *     DeltaCore is a Minecraft Java API Provider.
 *     Copyright (C) 2021 DeltaDevelopment
 *
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 */

package club.deltapvp.core.api;

import club.deltapvp.core.hex.HexValidatorProvider;
import club.deltapvp.core.hologram.HologramManagerProvider;
import club.deltapvp.core.bungeecordutil.BungeeCordProvider;
import club.deltapvp.core.file.FileLoaderProvider;
import club.deltapvp.core.inputlistener.InputListenerProvider;
import club.deltapvp.core.message.MessageProvider;
import club.deltapvp.core.serialize.BukkitSerializerProvider;
import club.deltapvp.core.sign.VirtualSignEditorProvider;
import club.deltapvp.core.skull.CustomSkullProvider;
import club.deltapvp.core.update.UpdateCheckerProvider;
import club.deltapvp.core.version.VersionCheckerProvider;
import club.deltapvp.api.DeltaAPI;
import club.deltapvp.api.bungeecord.BungeeCord;
import club.deltapvp.api.utilities.checker.UpdateChecker;
import club.deltapvp.api.utilities.file.FileLoader;
import club.deltapvp.api.utilities.hex.HexValidator;
import club.deltapvp.api.utilities.hologram.HologramManager;
import club.deltapvp.api.utilities.input.InputListener;
import club.deltapvp.api.utilities.message.iface.Message;
import club.deltapvp.api.utilities.serialization.BukkitSerializer;
import club.deltapvp.api.utilities.sign.VirtualSignEditor;
import club.deltapvp.api.utilities.skull.CustomSkull;
import club.deltapvp.api.utilities.version.VersionChecker;

import javax.annotation.Nonnull;
import java.util.List;

public class DeltaAPIProvider extends DeltaAPI {

    private final BungeeCord bungeeCord;
    private final InputListener inputListener;
    private final VersionChecker versionChecker;
    private final UpdateChecker updateChecker;
    private final FileLoader fileLoader;
    private final BukkitSerializer bukkitSerializer;
    private final HexValidator hexValidator;
    private final HologramManager hologramManager;
    private final VirtualSignEditor virtualSignEditor;

    public DeltaAPIProvider() {
        setInstance(this);

        bungeeCord = new BungeeCordProvider();
        inputListener = new InputListenerProvider();
        versionChecker = new VersionCheckerProvider();
        updateChecker = new UpdateCheckerProvider();
        fileLoader = new FileLoaderProvider();
        bukkitSerializer = new BukkitSerializerProvider();
        hexValidator = new HexValidatorProvider();
        hologramManager = new HologramManagerProvider();
        virtualSignEditor = new VirtualSignEditorProvider();

        new club.deltapvp.core.hologram.v2.HologramManagerProvider();
    }

    @Override
    public BungeeCord getBungeeCord() {
        return bungeeCord;
    }

    @Override
    public VersionChecker getVersionChecker() {
        return versionChecker;
    }

    @Override
    public InputListener getInputListener() {
        return inputListener;
    }

    @Override
    public Message createMessage(String... strings) {
        return new MessageProvider(strings);
    }

    @Override
    public Message createMessage( String s) {
        return new MessageProvider(s);
    }

    @Override
    public Message createMessage(List<String> list) {
        return new MessageProvider(list);
    }

    @Override
    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }

    @Override
    public FileLoader getFileLoader() {
        return fileLoader;
    }

    @Override
    public BukkitSerializer getBukkitSerializer() {
        return bukkitSerializer;
    }

    @Override
    public HexValidator getHexValidator() {
        return hexValidator;
    }

    @Override
    public HologramManager getHologramManager() {
        return hologramManager;
    }

    @Override
    public CustomSkull createCustomSkull(@Nonnull String url) {
        return new CustomSkullProvider(url);
    }

    @Override
    public VirtualSignEditor getVirtualSignEditor() {
        return virtualSignEditor;
    }
}
