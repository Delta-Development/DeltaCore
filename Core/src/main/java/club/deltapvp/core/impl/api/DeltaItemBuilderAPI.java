package club.deltapvp.core.impl.api;

import club.deltapvp.core.version.v1_11.VersionItemBuilder1_11;
import club.deltapvp.core.version.v1_12.VersionItemBuilder1_12;
import club.deltapvp.core.version.v1_13.VersionItemBuilder1_13;
import club.deltapvp.core.version.v1_14.VersionItemBuilder1_14;
import club.deltapvp.core.version.v1_15.VersionItemBuilder1_15;
import club.deltapvp.core.version.v1_16.VersionItemBuilder1_16;
import club.deltapvp.core.version.v1_17.VersionItemBuilder1_17;
import club.deltapvp.core.version.v1_8_8.VersionItemBuilder1_8;
import club.deltapvp.deltacore.api.DeltaAPI;
import club.deltapvp.deltacore.api.utilities.builder.itembuilder.APIItemBuilder;
import club.deltapvp.deltacore.api.utilities.builder.itembuilder.AbstractVersionItemBuilder;
import club.deltapvp.deltacore.api.utilities.version.ServerVersion;
import club.deltapvp.deltacore.api.utilities.version.VersionChecker;

public class DeltaItemBuilderAPI extends APIItemBuilder {

    private final AbstractVersionItemBuilder versionItemBuilder;

    public DeltaItemBuilderAPI() {
        setInstance(this);

        VersionChecker versionChecker = DeltaAPI.getInstance().getVersionChecker();
        ServerVersion version = versionChecker.getVersion();
        if (version.equals(ServerVersion.V1_17)) {
            versionItemBuilder = new VersionItemBuilder1_17();
            return;
        }
        if (version.equals(ServerVersion.V1_16)) {
            versionItemBuilder = new VersionItemBuilder1_16();
            return;
        }
        if (version.equals(ServerVersion.V1_15)) {
            versionItemBuilder = new VersionItemBuilder1_15();
            return;
        }

        if (version.equals(ServerVersion.V1_14)) {
            versionItemBuilder = new VersionItemBuilder1_14();
            return;
        }
        if (version.equals(ServerVersion.V1_13)) {
            versionItemBuilder = new VersionItemBuilder1_13();
            return;
        }
        if (version.equals(ServerVersion.V1_12)) {
            versionItemBuilder = new VersionItemBuilder1_12();
            return;
        }
        if (version.equals(ServerVersion.V1_11)) {
            versionItemBuilder = new VersionItemBuilder1_11();
        } else {
            versionItemBuilder = new VersionItemBuilder1_8();
        }
    }

    @Override
    public AbstractVersionItemBuilder getVersionItemBuilder() {
        return versionItemBuilder;
    }
}
