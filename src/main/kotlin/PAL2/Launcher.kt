import PAL2.GUI.CoreApplication
import SystemHandling.deleteFile
import SystemHandling.verifyFolder
import javafx.application.Application
import mu.KotlinLogging
import org.apache.commons.io.FileUtils
import org.kohsuke.github.GitHubBuilder
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.util.Locale
import kotlin.Exception

/**
 * Reads Data
 * Creates Data
 * Launches everything
 */
class Launcher2
{
    companion object
    {
        @JvmStatic
        fun main(args: Array<String>)
        {
            // TODO: Allow users to add existing addons
            // TODO: Allow users to add custom programs

            // Set Locale to US to avoid any locale issues.
            Locale.setDefault(Locale.US)

            if (!GlobalData.debugging)
            {
                var fInstall = File(Launcher2::class.java.protectionDomain.codeSource.location.toURI())

                if (fInstall.isFile)
                {
                    fInstall = fInstall.parentFile
                }
                GlobalData.install_dir = fInstall.path

                val stateONE = File("${GlobalData.pal_folder.path}${File.separator}1.state")
                val stateTWO = File("${GlobalData.pal_folder.path}${File.separator}2.state")
                val newest_update = File("${GlobalData.install_dir}${File.separator}latest")

                if (stateONE.exists())
                {
                    val install_dir = File(GlobalData.install_dir)
                    FileUtils.copyDirectory(install_dir, install_dir.parentFile)

                    deleteFile(stateONE)
                    stateTWO.createNewFile()
                    // Launch PAL2.exe in the parent file.
                    Runtime.getRuntime().exec("\"${install_dir.parentFile.path}${File.separator}PAL2.exe\"")
                    return
                }
                else if (stateTWO.exists())
                {
                    deleteFile(newest_update)
                    deleteFile(stateTWO)
                }
            }

            try
            {
                Application.launch(CoreApplication::class.java, *args)
            }
            catch (ex: Exception)
            {
                ex.printStackTrace()
            }
        }
    }
}