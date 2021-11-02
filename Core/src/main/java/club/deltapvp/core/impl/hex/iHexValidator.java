package club.deltapvp.core.impl.hex;

import club.deltapvp.core.abstractapi.AbstractHexValidator;
import club.deltapvp.core.version.v1_16.VersionHexValidator1_16;
import club.deltapvp.core.version.v1_17.VersionHexValidator1_17;
import club.deltapvp.deltacore.api.DeltaAPI;
import club.deltapvp.deltacore.api.utilities.hex.HexValidator;
import club.deltapvp.deltacore.api.utilities.version.ServerVersion;
import club.deltapvp.deltacore.api.utilities.version.VersionChecker;
import lombok.SneakyThrows;
import net.md_5.bungee.api.ChatColor;

public class iHexValidator implements HexValidator {
    private final boolean isHexVersion;
    private AbstractHexValidator abstractHexValidator;

    public iHexValidator() {
        boolean hexVersionChecker = false;

        VersionChecker versionChecker = DeltaAPI.getInstance().getVersionChecker();
        ServerVersion version = versionChecker.getVersion();
        if (version.equals(ServerVersion.V1_16)) {
            abstractHexValidator = new VersionHexValidator1_16();
            hexVersionChecker = true;
        }
        if (version.equals(ServerVersion.V1_17)) {
            abstractHexValidator = new VersionHexValidator1_17();
            hexVersionChecker = true;
        }
        isHexVersion = hexVersionChecker;
    }

    @SneakyThrows
    @Override
    public String validate(String input) {
        return (isHexVersion ? abstractHexValidator.validate(input) :
                ChatColor.translateAlternateColorCodes('&', input));
    }
}
