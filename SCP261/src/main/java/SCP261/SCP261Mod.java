package SCP261;

import SCP261.events.VendingMachineEvent;
import SCP261.potions.*;
import SCP261.relics.SadistsTumblerRelic;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import SCP261.util.IDCheckDontTouchPls;
import SCP261.util.TextureLoader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
// Please don't just mass replace "theDefault" with "yourMod" everywhere.
// It'll be a bigger pain for you. You only need to replace it in 3 places.
// I comment those places below, under the place where you set your ID.

//TODO: FIRST THINGS FIRST: RENAME YOUR PACKAGE AND ID NAMES FIRST-THING!!!
// Right click the package (Open the project pane on the left. Folder with black dot on it. The name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll down in this file. Change the ID from "theDefault:" to "yourModName:" or whatever your heart desires (don't use spaces). Dw, you'll see it.
// In the JSON strings (resources>localization>eng>[all them files] make sure they all go "yourModName:" rather than "theDefault". You can ctrl+R to replace in 1 file, or ctrl+shift+r to mass replace in specific files/directories (Be careful.).
// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class SCP261Mod implements
        //EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        //EditCharactersSubscriber,
        PostPotionUseSubscriber,
        PostInitializeSubscriber
       {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(SCP261Mod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties SCP261DefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "SCP261";
    private static final String AUTHOR = "Monochromia"; // And pretty soon - You!
    private static final String DESCRIPTION = "Adds some of http://www.scp-wiki.net/scp-261 's outputs as rare potions. Mod is a work in progress, and is intended to eventually add a new type of shop, with new relics, in addition to potions.";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    //public static final Color DEFAULT_GRAY = CardHelper.getColor(64.0f, 70.0f, 70.0f);
    
    // Potion Colors in RGB
    public static final Color LIGHTBLUE = CardHelper.getColor(62.0f, 243.0f, 243.0f);
    public static final Color LAFFERTY = CardHelper.getColor(245.f, 200.f, 34.f); // Orange-ish Yellow
    
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
  
    // Card backgrounds - The actual rectangular card.
    //private static final String ATTACK_DEFAULT_GRAY = "SCP261Resources/images/512/bg_attack_default_gray.png";
    //private static final String SKILL_DEFAULT_GRAY = "SCP261Resources/images/512/bg_skill_default_gray.png";
    //private static final String POWER_DEFAULT_GRAY = "SCP261Resources/images/512/bg_power_default_gray.png";
    
    //private static final String ENERGY_ORB_DEFAULT_GRAY = "SCP261Resources/images/512/card_default_gray_orb.png";
    //private static final String CARD_ENERGY_ORB = "SCP261Resources/images/512/card_small_orb.png";
    
    //private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "SCP261Resources/images/1024/bg_attack_default_gray.png";
    //private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "SCP261Resources/images/1024/bg_skill_default_gray.png";
    //private static final String POWER_DEFAULT_GRAY_PORTRAIT = "SCP261Resources/images/1024/bg_power_default_gray.png";
    //private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "SCP261Resources/images/1024/card_default_gray_orb.png";
    
    // Character assets
    //private static final String THE_DEFAULT_BUTTON = "SCP261Resources/images/charSelect/DefaultCharacterButton.png";
    //private static final String THE_DEFAULT_PORTRAIT = "SCP261Resources/images/charSelect/DefaultCharacterPortraitBG.png";
    //public static final String THE_DEFAULT_SHOULDER_1 = "SCP261Resources/images/char/defaultCharacter/shoulder.png";
    //public static final String THE_DEFAULT_SHOULDER_2 = "SCP261Resources/images/char/defaultCharacter/shoulder2.png";
    //public static final String THE_DEFAULT_CORPSE = "SCP261Resources/images/char/defaultCharacter/corpse.png";
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "SCP261Resources/images/Badge.png";
    
    // Atlas and JSON files for the Animations
    //public static final String THE_DEFAULT_SKELETON_ATLAS = "SCP261Resources/images/char/defaultCharacter/skeleton.atlas";
    //public static final String THE_DEFAULT_SKELETON_JSON = "SCP261Resources/images/char/defaultCharacter/skeleton.json";
    
    // =============== MAKE IMAGE PATHS =================
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }
    
    // =============== /MAKE IMAGE PATHS/ =================
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    
    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================
    
    public SCP261Mod() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
        
      /*
           (   ( /(  (     ( /( (            (  `   ( /( )\ )    )\ ))\ )
           )\  )\()) )\    )\()))\ )   (     )\))(  )\()|()/(   (()/(()/(
         (((_)((_)((((_)( ((_)\(()/(   )\   ((_)()\((_)\ /(_))   /(_))(_))
         )\___ _((_)\ _ )\ _((_)/(_))_((_)  (_()((_) ((_|_))_  _(_))(_))_
        ((/ __| || (_)_\(_) \| |/ __| __| |  \/  |/ _ \|   \  |_ _||   (_)
         | (__| __ |/ _ \ | .` | (_ | _|  | |\/| | (_) | |) |  | | | |) |
          \___|_||_/_/ \_\|_|\_|\___|___| |_|  |_|\___/|___/  |___||___(_)
      */
      
        setModID("SCP261");
        // cool
        
        // 1. Go to your resources folder in the project panel, and refactor> rename theDefaultResources to
        // yourModIDResources.
        
        // 2. Click on the localization > eng folder and press ctrl+shift+r, then select "Directory" (rather than in Project)
        // replace all instances of theDefault with yourModID.
        // Because your mod ID isn't the default. Your cards (and everything else) should have Your mod id. Not mine.
        
        // 3. FINALLY and most importantly: Scroll up a bit. You may have noticed the image locations above don't use getModID()
        // Change their locations to reflect your actual ID rather than theDefault. They get loaded before getID is a thing.
        
        logger.info("Done subscribing");
        
        //logger.info("Creating the color " + TheDefault.Enums.COLOR_GRAY.toString());

        //BaseMod.addColor(TheDefault.Enums.COLOR_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
        //        DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
        //        ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
        //        ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
        //        ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        //logger.info("Done creating the color");
        
        
        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        SCP261DefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("SCP261", "SCP261Config", SCP261DefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
    }
    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = SCP261Mod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO
    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NNOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = SCP261Mod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = SCP261Mod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    
    // ====== YOU CAN EDIT AGAIN ======
    
    
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing SCP261. Hi. =========================");
        SCP261Mod defaultmod = new SCP261Mod();
        logger.info("========================= /SCP261 Initialized. Hello World./ =========================");
    }
    
    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    //@Override
    //public void receiveEditCharacters() {
    //    logger.info("Beginning to edit characters. " + "Add " + TheDefault.Enums.THE_DEFAULT.toString());
    //
    //    //BaseMod.addCharacter(new TheDefault("the Default", TheDefault.Enums.THE_DEFAULT),
    //    //        THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, TheDefault.Enums.THE_DEFAULT);
    //
    //
    //    logger.info("Added " + TheDefault.Enums.THE_DEFAULT.toString());
    //}
    
    // =============== /LOAD THE CHARACTER/ =================
    
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("SCP261", "SCP261Config", SCP261DefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);


        receiveEditPotions();

        //Add events=======
        logger.info("Adding events");
        BaseMod.addEvent(VendingMachineEvent.ID, VendingMachineEvent.class);

        logger.info("Done adding events");
        //==================

        logger.info("Done loading badge Image and mod options");
    }
    
    // =============== / POST-INITIALIZE/ =================
    
    
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        
        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        BaseMod.addPotion(AppleJuicePotion.class, Color.RED, Color.FIREBRICK, (Color)null, AppleJuicePotion.POTION_ID);
        BaseMod.addPotion(BarrettColaPotion.class, Color.LIGHT_GRAY, Color.DARK_GRAY, (Color)null, BarrettColaPotion.POTION_ID);
        BaseMod.addPotion(BlastolaColaPotion.class, LIGHTBLUE, Color.BLUE, (Color)null, BlastolaColaPotion.POTION_ID);
        BaseMod.addPotion(BostonMolassesPotion.class, Color.BROWN, (Color)null, (Color)null, BostonMolassesPotion.POTION_ID);
        BaseMod.addPotion(ClockworkPotion.class, Color.LIGHT_GRAY, Color.CLEAR, (Color)null, ClockworkPotion.POTION_ID);
        BaseMod.addPotion(DietGhostPotion.class, LIGHTBLUE, (Color)null, (Color)null, DietGhostPotion.POTION_ID);
        BaseMod.addPotion(DewritoBlazePotion.class, Color.GREEN, Color.ORANGE, (Color)null, DewritoBlazePotion.POTION_ID);
        BaseMod.addPotion(EnergyGasPotion.class, Color.YELLOW, Color.ORANGE, (Color)null, EnergyGasPotion.POTION_ID);
        BaseMod.addPotion(GirlTearsPotion.class, Color.BLUE, LIGHTBLUE, (Color)null, GirlTearsPotion.POTION_ID);
        //BaseMod.addPotion(LaffertyPotion.class, LAFFERTY, Color.YELLOW, (Color)null, LaffertyPotion.POTION_ID);
        BaseMod.addPotion(PenguinPunchPotion.class, Color.BLUE, LIGHTBLUE, (Color)null, PenguinPunchPotion.POTION_ID);
        BaseMod.addPotion(SweetSwarmPotion.class, Color.PURPLE, Color.GREEN, (Color)null, SweetSwarmPotion.POTION_ID);
        BaseMod.addPotion(ThunderBarPotion.class, Color.FIREBRICK, Color.RED, (Color)null, ThunderBarPotion.POTION_ID);


        logger.info("Done editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================
    
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");
        BaseMod.addRelic(new SadistsTumblerRelic(), RelicType.SHARED);
        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        //BaseMod.addRelicToCustomPool(new PlaceholderRelic(), TheDefault.Enums.COLOR_GRAY);
        //BaseMod.addRelicToCustomPool(new BottledPlaceholderRelic(), TheDefault.Enums.COLOR_GRAY);
        //BaseMod.addRelicToCustomPool(new DefaultClickableRelic(), TheDefault.Enums.COLOR_GRAY);
        
        // This adds a relic to the Shared pool. Every character can find this relic.
        //BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);
        
        // Mark relics as seen (the others are all starters so they're marked as seen in the character file
        UnlockTracker.markRelicAsSeen(SadistsTumblerRelic.ID);
        logger.info("Done adding relics!");
}

    // ================ /ADD RELICS/ ===================


    // ================ ADD CARDS ===================

    //@Override
    //public void receiveEditCards() {
    //    logger.info("Adding variables");
    //    //Ignore this
    //    pathCheck();
    //    // Add the Custom Dynamic Variables
    //    logger.info("Add variabls");
    //    // Add the Custom Dynamic variabls
    //    BaseMod.addDynamicVariable(new DefaultCustomVariable());
    //    BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
    //
    //    logger.info("Adding cards");
    //    // Add the cards
    //    // Don't comment out/delete these cards (yet). You need 1 of each type and rarity (technically) for your game not to crash
    //    // when generating card rewards/shop screen items.
    //
    //    BaseMod.addCard(new OrbSkill());
    //    BaseMod.addCard(new DefaultSecondMagicNumberSkill());
    //    BaseMod.addCard(new DefaultCommonAttack());
    //    BaseMod.addCard(new DefaultAttackWithVariable());
    //    BaseMod.addCard(new DefaultCommonSkill());
    //    BaseMod.addCard(new DefaultCommonPower());
    //    BaseMod.addCard(new DefaultUncommonSkill());
    //    BaseMod.addCard(new DefaultUncommonAttack());
    //    BaseMod.addCard(new DefaultUncommonPower());
    //    BaseMod.addCard(new DefaultRareAttack());
    //    BaseMod.addCard(new DefaultRareSkill());
    //    BaseMod.addCard(new DefaultRarePower());
    //
    //    logger.info("Making sure the cards are unlocked.");
    //    // Unlock the cards
    //    // This is so that they are all "seen" in the library, for people who like to look at the card list
    //    // before playing your mod.
    //    UnlockTracker.unlockCard(OrbSkill.ID);
    //    UnlockTracker.unlockCard(DefaultSecondMagicNumberSkill.ID);
    //    UnlockTracker.unlockCard(DefaultCommonAttack.ID);
    //    UnlockTracker.unlockCard(DefaultAttackWithVariable.ID);
    //    UnlockTracker.unlockCard(DefaultCommonSkill.ID);
    //    UnlockTracker.unlockCard(DefaultCommonPower.ID);
    //    UnlockTracker.unlockCard(DefaultUncommonSkill.ID);
    //    UnlockTracker.unlockCard(DefaultUncommonAttack.ID);
    //    UnlockTracker.unlockCard(DefaultUncommonPower.ID);
    //    UnlockTracker.unlockCard(DefaultRareAttack.ID);
    //    UnlockTracker.unlockCard(DefaultRareSkill.ID);
    //    UnlockTracker.unlockCard(DefaultRarePower.ID);
    //
    //    logger.info("Done adding cards!");
    //}
    
    // There are better ways to do this than listing every single individual card, but I do not want to complicate things
    // in a "tutorial" mod. This will do and it's completely ok to use. If you ever want to clean up and
    // shorten all the imports, go look take a look at other mods, such as Hubris.
    
    // ================ /ADD CARDS/ ===================
    
    
    // ================ LOAD THE TEXT ===================
    
    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        
        // CardStrings
        //BaseMod.loadCustomStringsFile(CardStrings.class,
        //getModID() + "Resources/localization/eng/SCP261-Card-Strings.json");
        
        // PowerStrings
        //BaseMod.loadCustomStringsFile(PowerStrings.class,
        //        getModID() + "Resources/localization/eng/SCP261-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/SCP261-Relic-Strings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/SCP261-Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/SCP261-Potion-Strings.json");
        
        // CharacterStrings
        //BaseMod.loadCustomStringsFile(CharacterStrings.class,
        //        getModID() + "Resources/localization/eng/SCP261-Character-Strings.json");
        
        // OrbStrings
        //BaseMod.loadCustomStringsFile(OrbStrings.class,
         //       getModID() + "Resources/localization/eng/SCP261-Orb-Strings.json");
        
        logger.info("Done editting strings");
    }
    
    // ================ /LOAD THE TEXT/ ===================
    
    // ================ LOAD THE KEYWORDS ===================
    
    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        
        //Gson gson = new Gson();
        //String json = Gdx.files.internal(getModID() + "Resources/localization/eng/SCP261-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        //com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        //
        //if (keywords != null) {
        //    for (Keyword keyword : keywords) {
        //        BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
        //        //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
        //    }
        //}
    }
    
    // ================ /LOAD THE KEYWORDS/ ===================    
    
    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receivePostPotionUse(AbstractPotion abstractPotion) {
        AbstractPlayer p = AbstractDungeon.player;
        if(p.hasRelic("SCP261:SadistsTumblerRelic"))
        {
            SadistsTumblerRelic a = (SadistsTumblerRelic) p.getRelic("SCP261:SadistsTumblerRelic");
            a.receivePostPotionUse(abstractPotion);
            logger.info("Sadist's Tumbler post potion use");
        }
    }
}
