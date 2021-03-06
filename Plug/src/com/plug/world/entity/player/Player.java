package com.plug.world.entity.player;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;

import org.apache.mina.common.IoSession;

import com.plug.Constants;
import com.plug.Game;
import com.plug.core.net.Stream;
import com.plug.core.net.protocol.packet.Packet;
import com.plug.core.net.protocol.packet.PacketHandler;
import com.plug.core.net.protocol.packet.StaticPacketBuilder;
import com.plug.core.net.security.HostList;
import com.plug.core.net.security.ISAACCipher;
import com.plug.util.Colors;
import com.plug.util.Misc;
import com.plug.world.entity.Entity;
import com.plug.world.entity.npc.Npc;
import com.plug.world.entity.npc.NpcHandler;
import com.plug.world.item.Item;
import com.plug.world.item.ItemAssistant;
import com.plug.world.shop.ShopAssistant;

public class Player extends Entity {
	
	public boolean isMember = false;
	public boolean isMuted = false;
	
	public PlayerRights rights = PlayerRights.PLAYER;

    public ArrayList<String> killedPlayers = new ArrayList<String>();
    public ArrayList<Integer> attackedPlayers = new ArrayList<Integer>();

    public long lastButton;

    public ArrayList<String> lastKilledPlayers = new ArrayList<String>();

    public long lastCast = 0;

    public boolean initialized = false, disconnected = false,
            ruleAgreeButton = false, RebuildNPCList = false, isActive = false,
            isKicked = false, isSkulled = false, friendUpdate = false,
            newPlayer = false, hasMultiSign = false, saveCharacter = false,
            mouseButton = false, splitChat = false, chatEffects = true,
            acceptAid = false, nextDialogue = false, autocasting = false,
            usedSpecial = false, mageFollow = false, dbowSpec = false,
            craftingLeather = false, properLogout = false, secDbow = false,
            maxNextHit = false, ssSpec = false, vengOn = false,
            addStarter = false, accountFlagged = false, msbSpec = false;

    public int

    saveDelay, playerKilled, pkPoints, totalPlayerDamageDealt, killedBy,
            lastChatId = 1, privateChat, friendSlot = 0, dialogueId,
            randomCoffin, newLocation, specEffect, specBarId, attackLevelReq,
            defenceLevelReq, strengthLevelReq, rangeLevelReq, magicLevelReq,
            followId, skullTimer, votingPoints, nextChat = 0, talkingNpc = -1,
            dialogueAction = 0, autocastId, followDistance, followId2,
            barrageCount = 0, delayedDamage = 0, delayedDamage2 = 0,
            pcPoints = 0, magePoints = 0, desertTreasure = 0,
            lastArrowUsed = -1, clanId = -1, autoRet = 0, pcDamage = 0,
            xInterfaceId = 0, xRemoveId = 0, xRemoveSlot = 0, tzhaarToKill = 0,
            tzhaarKilled = 0, waveId, frozenBy = 0, poisonDamage = -1,
            teleAction = 0, bonusAttack = 0, lastNpcAttacked = 0,
            killCount = 0;
    public String clanName, properName;
    public int[] voidStatus = new int[5];
    public int[] itemKeptId = new int[4];
    public int[] pouches = new int[4];
    public final int[] POUCH_SIZE = { 3, 6, 9, 12 };
    public boolean[] invSlot = new boolean[28], equipSlot = new boolean[14];
    public long friends[] = new long[200];
    public double specAmount = 0;
    public double specAccuracy = 1;
    public double specDamage = 1;
    public double prayerPoint = 1.0;
    public int teleGrabItem, teleGrabX, teleGrabY, duelCount, underAttackBy,
            underAttackBy2, wildLevel, teleTimer, respawnTimer, saveTimer = 0,
            teleBlockLength, poisonDelay;
    public long lastPlayerMove, lastPoison, lastPoisonSip, poisonImmune,
            lastSpear, lastProtItem, dfsDelay, lastVeng, lastYell,
            teleGrabDelay, protMageDelay, protMeleeDelay, protRangeDelay,
            lastAction, lastThieve, lastLockPick, alchDelay,
            specDelay = System.currentTimeMillis(), duelDelay, teleBlockDelay,
            godSpellDelay, singleCombatDelay, singleCombatDelay2, reduceStat,
            restoreStatsDelay, logoutDelay, buryDelay, foodDelay, potDelay;
    public boolean canChangeAppearance = false;
    public boolean mageAllowed;
    public byte buffer[] = null;
    public Stream inStream = null, outStream = null;
    private IoSession session;
    private ItemAssistant itemAssistant = new ItemAssistant(this);
    private ShopAssistant shopAssistant = new ShopAssistant(this);
    private TradeAndDuel tradeAndDuel = new TradeAndDuel(this);
    private PlayerAssistant playerAssistant = new PlayerAssistant(this);
    private CombatAssistant combatAssistant = new CombatAssistant(this);
    private ActionHandler actionHandler = new ActionHandler(this);
    private DialogueHandler dialogueHandler = new DialogueHandler(this);
    private ConcurrentLinkedQueue<Packet> queuedPackets = new ConcurrentLinkedQueue<Packet>();
    private Potions potions = new Potions(this);
    private PotionMixing potionMixing = new PotionMixing(this);
    private Food food = new Food(this);
    private int somejunk;
    public int lowMemoryVersion = 0;
    public int timeOutCounter = 0;
    public int returnCode = 2;
    private Future<?> currentTask;

    public final int[] BOWS = { 9185, 839, 845, 847, 851, 855, 859, 841, 843, 849, 853, 857, 861, 4212, 4214, 4215, 11235, 4216, 4217, 4218, 4219, 4220, 4221, 4222, 4223, 6724, 4734, 4934, 4935, 4936, 4937 };
    public final int[] ARROWS = { 882, 884, 886, 888, 890, 892, 4740, 11212, 9140, 9141, 4142, 9143, 9144, 9240, 9241, 9242, 9243, 9244, 9245 };
    public final int[] NO_ARROW_DROP = { 4212, 4214, 4215, 4216, 4217, 4218, 4219, 4220, 4221, 4222, 4223, 4734, 4934, 4935, 4936, 4937 };
    public final int[] OTHER_RANGE_WEAPONS = { 863, 864, 865, 866, 867, 868, 869, 806, 807, 808, 809, 810, 811, 825, 826, 827, 828, 829, 830, 800, 801, 802, 803, 804, 805, 6522 };

    public final int[][] MAGIC_SPELLS = {
    // example {magicId, level req, animation, startGFX, projectile Id,
    // endGFX, maxhit, exp gained, rune 1, rune 1 amount, rune 2, rune 2
    // amount, rune 3, rune 3 amount, rune 4, rune 4 amount}

    // Modern Spells
    { 1152, 1, 711, 90, 91, 92, 2, 5, 556, 1, 558, 1, 0, 0, 0, 0 }, // wind
    // strike
    { 1154, 5, 711, 93, 94, 95, 4, 7, 555, 1, 556, 1, 558, 1, 0, 0 }, // water
    // strike
    { 1156, 9, 711, 96, 97, 98, 6, 9, 557, 2, 556, 1, 558, 1, 0, 0 },// earth
    // strike
    { 1158, 13, 711, 99, 100, 101, 8, 11, 554, 3, 556, 2, 558, 1, 0, 0 }, // fire
    // strike
    { 1160, 17, 711, 117, 118, 119, 9, 13, 556, 2, 562, 1, 0, 0, 0, 0 }, // wind
    // bolt
    { 1163, 23, 711, 120, 121, 122, 10, 16, 556, 2, 555, 2, 562, 1, 0, 0 }, // water
    // bolt
    { 1166, 29, 711, 123, 124, 125, 11, 20, 556, 2, 557, 3, 562, 1, 0, 0 }, // earth
    // bolt
    { 1169, 35, 711, 126, 127, 128, 12, 22, 556, 3, 554, 4, 562, 1, 0, 0 }, // fire
    // bolt
    { 1172, 41, 711, 132, 133, 134, 13, 25, 556, 3, 560, 1, 0, 0, 0, 0 }, // wind
    // blast
    { 1175, 47, 711, 135, 136, 137, 14, 28, 556, 3, 555, 3, 560, 1, 0, 0 }, // water
    // blast
    { 1177, 53, 711, 138, 139, 140, 15, 31, 556, 3, 557, 4, 560, 1, 0, 0 }, // earth
    // blast
    { 1181, 59, 711, 129, 130, 131, 16, 35, 556, 4, 554, 5, 560, 1, 0, 0 }, // fire
    // blast
    { 1183, 62, 711, 158, 159, 160, 17, 36, 556, 5, 565, 1, 0, 0, 0, 0 }, // wind
    // wave
    { 1185, 65, 711, 161, 162, 163, 18, 37, 556, 5, 555, 7, 565, 1, 0, 0 }, // water
    // wave
    { 1188, 70, 711, 164, 165, 166, 19, 40, 556, 5, 557, 7, 565, 1, 0, 0 }, // earth
    // wave
    { 1189, 75, 711, 155, 156, 157, 20, 42, 556, 5, 554, 7, 565, 1, 0, 0 }, // fire
    // wave
    { 1153, 3, 716, 102, 103, 104, 0, 13, 555, 3, 557, 2, 559, 1, 0, 0 }, // confuse
    { 1157, 11, 716, 105, 106, 107, 0, 20, 555, 3, 557, 2, 559, 1, 0, 0 }, // weaken
    { 1161, 19, 716, 108, 109, 110, 0, 29, 555, 2, 557, 3, 559, 1, 0, 0 }, // curse
    { 1542, 66, 729, 167, 168, 169, 0, 76, 557, 5, 555, 5, 566, 1, 0, 0 }, // vulnerability
    { 1543, 73, 729, 170, 171, 172, 0, 83, 557, 8, 555, 8, 566, 1, 0, 0 }, // enfeeble
    { 1562, 80, 729, 173, 174, 107, 0, 90, 557, 12, 555, 12, 556, 1, 0, 0 }, // stun
    { 1572, 20, 711, 177, 178, 181, 0, 30, 557, 3, 555, 3, 561, 2, 0, 0 }, // bind
    { 1582, 50, 711, 177, 178, 180, 2, 60, 557, 4, 555, 4, 561, 3, 0, 0 }, // snare
    { 1592, 79, 711, 177, 178, 179, 4, 90, 557, 5, 555, 5, 561, 4, 0, 0 }, // entangle
    { 1171, 39, 724, 145, 146, 147, 15, 25, 556, 2, 557, 2, 562, 1, 0, 0 }, // crumble
    // undead
    { 1539, 50, 708, 87, 88, 89, 25, 42, 554, 5, 560, 1, 0, 0, 0, 0 }, // iban
    // blast
    { 12037, 50, 1576, 327, 328, 329, 19, 30, 560, 1, 558, 4, 0, 0, 0, 0 }, // magic
    // dart
    { 1190, 60, 811, 0, 0, 76, 20, 60, 554, 2, 565, 2, 556, 4, 0, 0 }, // sara
    // strike
    { 1191, 60, 811, 0, 0, 77, 20, 60, 554, 1, 565, 2, 556, 4, 0, 0 }, // cause
    // of
    // guthix
    { 1192, 60, 811, 0, 0, 78, 20, 60, 554, 4, 565, 2, 556, 1, 0, 0 }, // flames
    // of
    // zammy
    { 12445, 85, 1819, 0, 344, 345, 0, 65, 563, 1, 562, 1, 560, 1, 0, 0 }, // teleblock

    // Ancient Spells
    { 12939, 50, 1978, 0, 384, 385, 13, 30, 560, 2, 562, 2, 554, 1, 556, 1 }, // smoke
    // rush
    { 12987, 52, 1978, 0, 378, 379, 14, 31, 560, 2, 562, 2, 566, 1, 556, 1 }, // shadow
    // rush
    { 12901, 56, 1978, 0, 0, 373, 15, 33, 560, 2, 562, 2, 565, 1, 0, 0 }, // blood
    // rush
    { 12861, 58, 1978, 0, 360, 361, 16, 34, 560, 2, 562, 2, 555, 2, 0, 0 }, // ice
    // rush
    { 12963, 62, 1979, 0, 0, 389, 19, 36, 560, 2, 562, 4, 556, 2, 554, 2 }, // smoke
    // burst
    { 13011, 64, 1979, 0, 0, 382, 20, 37, 560, 2, 562, 4, 556, 2, 566, 2 }, // shadow
    // burst
    { 12919, 68, 1979, 0, 0, 376, 21, 39, 560, 2, 562, 4, 565, 2, 0, 0 }, // blood
    // burst
    { 12881, 70, 1979, 0, 0, 363, 22, 40, 560, 2, 562, 4, 555, 4, 0, 0 }, // ice
    // burst
    { 12951, 74, 1978, 0, 386, 387, 23, 42, 560, 2, 554, 2, 565, 2, 556, 2 }, // smoke
    // blitz
    { 12999, 76, 1978, 0, 380, 381, 24, 43, 560, 2, 565, 2, 556, 2, 566, 2 }, // shadow
    // blitz
    { 12911, 80, 1978, 0, 374, 375, 25, 45, 560, 2, 565, 4, 0, 0, 0, 0 }, // blood
    // blitz
    { 12871, 82, 1978, 366, 0, 367, 26, 46, 560, 2, 565, 2, 555, 3, 0, 0 }, // ice
    // blitz
    { 12975, 86, 1979, 0, 0, 391, 27, 48, 560, 4, 565, 2, 556, 4, 554, 4 }, // smoke
    // barrage
    { 13023, 88, 1979, 0, 0, 383, 28, 49, 560, 4, 565, 2, 556, 4, 566, 3 }, // shadow
    // barrage
    { 12929, 92, 1979, 0, 0, 377, 29, 51, 560, 4, 565, 4, 566, 1, 0, 0 }, // blood
    // barrage
    { 12891, 94, 1979, 0, 0, 369, 30, 52, 560, 4, 565, 2, 555, 6, 0, 0 }, // ice
    // barrage

    { -1, 80, 811, 301, 0, 0, 0, 0, 554, 3, 565, 3, 556, 3, 0, 0 }, // charge
    { -1, 21, 712, 112, 0, 0, 0, 10, 554, 3, 561, 1, 0, 0, 0, 0 }, // low
    // alch
    { -1, 55, 713, 113, 0, 0, 0, 20, 554, 5, 561, 1, 0, 0, 0, 0 }, // high
    // alch
    { -1, 33, 728, 142, 143, 144, 0, 35, 556, 1, 563, 1, 0, 0, 0, 0 } // telegrab

    };

    public boolean isAutoButton(int button) {
        for (int j = 0; j < autocastIds.length; j += 2) {
            if (autocastIds[j] == button)
                return true;
        }
        return false;
    }

    public int[] autocastIds = { 51133, 32, 51185, 33, 51091, 34, 24018, 35, 51159, 36, 51211, 37, 51111, 38, 51069, 39, 51146, 40, 51198, 41, 51102, 42, 51058, 43, 51172, 44, 51224, 45, 51122, 46, 51080, 47, 7038, 0, 7039, 1, 7040, 2, 7041, 3, 7042, 4, 7043, 5, 7044, 6, 7045, 7, 7046, 8, 7047, 9, 7048, 10, 7049, 11, 7050, 12, 7051, 13, 7052, 14, 7053, 15, 47019, 27, 47020, 25, 47021, 12, 47022, 13, 47023, 14, 47024, 15 };

    // public String spellName = "Select Spell";
    public void assignAutocast(int button) {
        for (int j = 0; j < autocastIds.length; j++) {
            if (autocastIds[j] == button) {
                Player c = Game.playerHandler.players[this.slot];
                autocasting = true;
                autocastId = autocastIds[j + 1];
                c.getPA().sendFrame36(108, 1);
                c.setSidebarInterface(0, 328);
                // spellName = getSpellName(autocastId);
                // spellName = spellName;
                // c.getPA().sendFrame126(spellName, 354);
                c = null;
                break;
            }
        }
    }

    public String getSpellName(int id) {
        switch (id) {
            case 0:
                return "Air Strike";
            case 1:
                return "Water Strike";
            case 2:
                return "Earth Strike";
            case 3:
                return "Fire Strike";
            case 4:
                return "Air Bolt";
            case 5:
                return "Water Bolt";
            case 6:
                return "Earth Bolt";
            case 7:
                return "Fire Bolt";
            case 8:
                return "Air Blast";
            case 9:
                return "Water Blast";
            case 10:
                return "Earth Blast";
            case 11:
                return "Fire Blast";
            case 12:
                return "Air Wave";
            case 13:
                return "Water Wave";
            case 14:
                return "Earth Wave";
            case 15:
                return "Fire Wave";
            case 32:
                return "Shadow Rush";
            case 33:
                return "Smoke Rush";
            case 34:
                return "Blood Rush";
            case 35:
                return "Ice Rush";
            case 36:
                return "Shadow Burst";
            case 37:
                return "Smoke Burst";
            case 38:
                return "Blood Burst";
            case 39:
                return "Ice Burst";
            case 40:
                return "Shadow Blitz";
            case 41:
                return "Smoke Blitz";
            case 42:
                return "Blood Blitz";
            case 43:
                return "Ice Blitz";
            case 44:
                return "Shadow Barrage";
            case 45:
                return "Smoke Barrage";
            case 46:
                return "Blood Barrage";
            case 47:
                return "Ice Barrage";
            default:
                return "Select Spell";
        }
    }

    public boolean fullVoidRange() {
        return playerEquipment[playerHat] == 11664 && playerEquipment[playerLegs] == 8840 && playerEquipment[playerChest] == 8839 && playerEquipment[playerHands] == 8842;
    }

    public boolean fullVoidMage() {
        return playerEquipment[playerHat] == 11663 && playerEquipment[playerLegs] == 8840 && playerEquipment[playerChest] == 8839 && playerEquipment[playerHands] == 8842;
    }

    public boolean fullVoidMelee() {
        return playerEquipment[playerHat] == 11665 && playerEquipment[playerLegs] == 8840 && playerEquipment[playerChest] == 8839 && playerEquipment[playerHands] == 8842;
    }

    public int[][] barrowsNpcs = { { 2030, 0 }, // verac
    { 2029, 0 }, // toarg
    { 2028, 0 }, // karil
    { 2027, 0 }, // guthan
    { 2026, 0 }, // dharok
    { 2025, 0 } // ahrim
    };
    public int barrowsKillCount;

    public int reduceSpellId;
    public final int[] REDUCE_SPELL_TIME = { 250000, 250000, 250000, 500000, 500000, 500000 }; // how
    // long
    // does
    // the
    // other
    // player
    // stay
    // immune
    // to
    // the spell
    public long[] reduceSpellDelay = new long[6];
    public final int[] REDUCE_SPELLS = { 1153, 1157, 1161, 1542, 1543, 1562 };
    public boolean[] canUseReducingSpell = { true, true, true, true, true, true };

    public int slayerTask, taskAmount;

    public int prayerId = -1;
    public int headIcon = -1;
    public int bountyIcon = 0;
    public long stopPrayerDelay, prayerDelay;
    public boolean usingPrayer;
    public final int[] PRAYER_DRAIN_RATE = { 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500 };
    public final int[] PRAYER_LEVEL_REQUIRED = { 1, 4, 7, 8, 9, 10, 13, 16, 19, 22, 25, 26, 27, 28, 31, 34, 37, 40, 43, 44, 45, 46, 49, 52, 60, 70 };
    public final int[] PRAYER = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25 };
    public final String[] PRAYER_NAME = { "Thick Skin", "Burst of Strength", "Clarity of Thought", "Sharp Eye", "Mystic Will", "Rock Skin", "Superhuman Strength", "Improved Reflexes", "Rapid Restore", "Rapid Heal", "Protect Item", "Hawk Eye", "Mystic Lore", "Steel Skin", "Ultimate Strength", "Incredible Reflexes", "Protect from Magic", "Protect from Missiles", "Protect from Melee", "Eagle Eye", "Mystic Might", "Retribution", "Redemption", "Smite", "Chivalry", "Piety" };
    public final int[] PRAYER_GLOW = { 83, 84, 85, 601, 602, 86, 87, 88, 89, 90, 91, 603, 604, 92, 93, 94, 95, 96, 97, 605, 606, 98, 99, 100, 607, 608 };
    public final int[] PRAYER_HEAD_ICONS = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 2, 1, 0, -1, -1, 3, 5, 4, -1, -1 };
    // {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,3,2,1,4,6,5};

    public boolean[] prayerActive = { false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false };

    public int duelTimer, duelTeleX, duelTeleY, duelSlot, duelSpaceReq,
            duelOption, duelingWith, duelStatus;
    public int headIconPk = -1, headIconHints;
    public boolean duelRequested;
    public boolean[] duelRule = new boolean[22];
    public final int[] DUEL_RULE_ID = { 1, 2, 16, 32, 64, 128, 256, 512, 1024, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 2097152, 8388608, 16777216, 67108864, 134217728 };

    public boolean doubleHit, usingSpecial, npcDroppingItems, usingRangeWeapon,
            usingBow, usingMagic, castingMagic;
    public int specMaxHitIncrease, freezeDelay, freezeTimer = -6, killerId,
            playerIndex, oldPlayerIndex, lastWeaponUsed, projectileStage,
            crystalBowArrowCount, playerMagicBook, teleGfx, teleEndAnimation,
            teleHeight, teleX, teleY, rangeItemUsed, killingNpcIndex,
            totalDamageDealt, oldNpcIndex, fightMode, attackTimer, npcIndex,
            npcClickIndex, npcType, castingSpellId, oldSpellId, spellId,
            hitDelay;
    public boolean magicFailed, oldMagicFailed;
    public int bowSpecShot, clickNpcType, clickObjectType, objectId, objectX,
            objectY, objectXOffset, objectYOffset, objectDistance;
    public int pItemX, pItemY, pItemId;
    public boolean isMoving, walkingToItem;
    public boolean isShopping, updateShop;
    public int myShopId;
    public int tradeStatus, tradeWith;
    public boolean inDuel, tradeAccepted, goodTrade, inTrade, tradeRequested,
            tradeResetNeeded, tradeConfirmed, tradeConfirmed2, canOffer,
            acceptTrade, acceptedTrade;
    public int attackAnim;
    public int[] playerBonus = new int[12];
    public boolean isRunning2 = true;
    public boolean takeAsNote;
    public int combatLevel;
    public boolean saveFile = false;
    public int playerAppearance[] = new int[13];
    public int apset;
    public int actionID;
    public int wearItemTimer, wearId, wearSlot, interfaceId;
    public int XremoveSlot, XinterfaceID, XremoveID, Xamount;

    public int tutorial = 15;
    public boolean usingGlory = false;
    public int[] woodcut = new int[3];
    public int wcTimer = 0;
    public int[] mining = new int[3];
    public int miningTimer = 0;
    public boolean fishing = false;
    public int fishTimer = 0;
    public int smeltType; // 1 = bronze, 2 = iron, 3 = steel, 4 = gold, 5 =
    // mith, 6 = addy, 7 = rune
    public int smeltAmount;
    public int smeltTimer = 0;
    public boolean smeltInterface;
    public boolean patchCleared;
    public int[] farm = new int[2];

    public boolean antiFirePot = false;

    /**
     * Castle Wars
     */
    public int castleWarsTeam;
    public boolean inCwGame;
    public boolean inCwWait;

    /**
     * Fight Pits
     */
    public boolean inPits = false;
    public int pitsStatus = 0;

    /**
     * SouthWest, NorthEast, SouthWest, NorthEast
     */

    public boolean isInTut() {
        if (absX >= 2625 && absX <= 2687 && absY >= 4670 && absY <= 4735) {
            return true;
        }
        return false;
    }

    public boolean inBarrows() {
        if (absX > 3520 && absX < 3598 && absY > 9653 && absY < 9750) {
            return true;
        }
        return false;
    }

    public boolean inArea(int x, int y, int x1, int y1) {
        if (absX > x && absX < x1 && absY < y && absY > y1) {
            return true;
        }
        return false;
    }

    public boolean inWild() {
        if (absX > 2941 && absX < 3392 && absY > 3518 && absY < 3966 || absX > 2941 && absX < 3392 && absY > 9918 && absY < 10366) {
            return true;
        }
        return false;
    }

    public boolean arenas() {
        if (absX > 3331 && absX < 3391 && absY > 3242 && absY < 3260) {
            return true;
        }
        return false;
    }

    public boolean inDuelArena() {
        if ((absX > 3322 && absX < 3394 && absY > 3195 && absY < 3291) || (absX > 3311 && absX < 3323 && absY > 3223 && absY < 3248)) {
            return true;
        }
        return false;
    }

    public boolean inMulti() {
        if ((absX >= 3136 && absX <= 3327 && absY >= 3519 && absY <= 3607) || (absX >= 3190 && absX <= 3327 && absY >= 3648 && absY <= 3839) || (absX >= 3200 && absX <= 3390 && absY >= 3840 && absY <= 3967) || (absX >= 2992 && absX <= 3007 && absY >= 3912 && absY <= 3967) || (absX >= 2946 && absX <= 2959 && absY >= 3816 && absY <= 3831) || (absX >= 3008 && absX <= 3199 && absY >= 3856 && absY <= 3903) || (absX >= 3008 && absX <= 3071 && absY >= 3600 && absY <= 3711) || (absX >= 3072 && absX <= 3327 && absY >= 3608 && absY <= 3647) || (absX >= 2624 && absX <= 2690 && absY >= 2550 && absY <= 2619) || (absX >= 2371 && absX <= 2422 && absY >= 5062 && absY <= 5117) || (absX >= 2896 && absX <= 2927 && absY >= 3595 && absY <= 3630) || (absX >= 2892 && absX <= 2932 && absY >= 4435 && absY <= 4464) || (absX >= 2256 && absX <= 2287 && absY >= 4680 && absY <= 4711)) {
            return true;
        }
        return false;
    }

    public boolean inFightCaves() {
        return absX >= 2360 && absX <= 2445 && absY >= 5045 && absY <= 5125;
    }

    public boolean inPirateHouse() {
        return absX >= 3038 && absX <= 3044 && absY >= 3949 && absY <= 3959;
    }

    public String connectedFrom = "";
    public String globalMessage = "";

    public String playerName = null;
    public String playerName2 = null;
    public String playerPass = null;
    public int playerRights;
    public PlayerHandler handler = null;
    public int playerItems[] = new int[28];
    public int playerItemsN[] = new int[28];
    public int bankItems[] = new int[Constants.BANK_SIZE];
    public int bankItemsN[] = new int[Constants.BANK_SIZE];
    public boolean bankNotes = false;

    public int playerStandIndex = 0x328;
    public int playerTurnIndex = 0x337;
    public int playerWalkIndex = 0x333;
    public int playerTurn180Index = 0x334;
    public int playerTurn90CWIndex = 0x335;
    public int playerTurn90CCWIndex = 0x336;
    public int playerRunIndex = 0x338;

    public int playerHat = 0;
    public int playerCape = 1;
    public int playerAmulet = 2;
    public int playerWeapon = 3;
    public int playerChest = 4;
    public int playerShield = 5;
    public int playerLegs = 7;
    public int playerHands = 9;
    public int playerFeet = 10;
    public int playerRing = 12;
    public int playerArrows = 13;

    public int playerAttack = 0;
    public int playerDefence = 1;
    public int playerStrength = 2;
    public int playerHitpoints = 3;
    public int playerRanged = 4;
    public int playerPrayer = 5;
    public int playerMagic = 6;
    public int playerCooking = 7;
    public int playerWoodcutting = 8;
    public int playerFletching = 9;
    public int playerFishing = 10;
    public int playerFiremaking = 11;
    public int playerCrafting = 12;
    public int playerSmithing = 13;
    public int playerMining = 14;
    public int playerHerblore = 15;
    public int playerAgility = 16;
    public int playerThieving = 17;
    public int playerSlayer = 18;
    public int playerFarming = 19;
    public int playerRunecrafting = 20;

    public int[] playerEquipment = new int[14];
    public int[] playerEquipmentN = new int[14];
    public int[] playerLevel = new int[25];
    public int[] playerXP = new int[25];

    public void updateshop(int i) {
        Player p = Game.playerHandler.players[slot];
        p.getShops().resetShop(i);
    }

    public void println_debug(String str) {
        System.out.println("[player-" + slot + "]: " + str);
    }

    public void println(String str) {
        System.out.println("[player-" + slot + "]: " + str);
    }

    public Player(IoSession s, int _playerId) {
        slot = _playerId;
        playerRights = 0;

        for (int i = 0; i < playerItems.length; i++) {
            playerItems[i] = 0;
        }
        for (int i = 0; i < playerItemsN.length; i++) {
            playerItemsN[i] = 0;
        }

        for (int i = 0; i < playerLevel.length; i++) {
            if (i == 3) {
                playerLevel[i] = 10;
            } else {
                playerLevel[i] = 1;
            }
        }

        for (int i = 0; i < playerXP.length; i++) {
            if (i == 3) {
                playerXP[i] = 1300;
            } else {
                playerXP[i] = 0;
            }
        }
        for (int i = 0; i < Constants.BANK_SIZE; i++) {
            bankItems[i] = 0;
        }

        for (int i = 0; i < Constants.BANK_SIZE; i++) {
            bankItemsN[i] = 0;
        }

        playerAppearance[0] = 0; // gender
        playerAppearance[1] = 7; // head
        playerAppearance[2] = 25;// Torso
        playerAppearance[3] = 29; // arms
        playerAppearance[4] = 35; // hands
        playerAppearance[5] = 39; // legs
        playerAppearance[6] = 44; // feet
        playerAppearance[7] = 14; // beard
        playerAppearance[8] = 7; // hair colour
        playerAppearance[9] = 8; // torso colour
        playerAppearance[10] = 9; // legs colour
        playerAppearance[11] = 5; // feet colour
        playerAppearance[12] = 0; // skin colour

        apset = 0;
        actionID = 0;

        playerEquipment[playerHat] = -1;
        playerEquipment[playerCape] = -1;
        playerEquipment[playerAmulet] = -1;
        playerEquipment[playerChest] = -1;
        playerEquipment[playerShield] = -1;
        playerEquipment[playerLegs] = -1;
        playerEquipment[playerHands] = -1;
        playerEquipment[playerFeet] = -1;
        playerEquipment[playerRing] = -1;
        playerEquipment[playerArrows] = -1;
        playerEquipment[playerWeapon] = -1;

        heightLevel = 0;

        teleportToX = Constants.START_LOCATION_X;
        teleportToY = Constants.START_LOCATION_Y;

        absX = absY = -1;
        mapRegionX = mapRegionY = -1;
        currentX = currentY = 0;
        resetWalkingQueue();
        this.session = s;
        synchronized (this) {
            outStream = new Stream(new byte[Constants.BUFFER_SIZE]);
            outStream.currentOffset = 0;
        }
        inStream = new Stream(new byte[Constants.BUFFER_SIZE]);
        inStream.currentOffset = 0;
        buffer = new byte[Constants.BUFFER_SIZE];
    }

    public void flushOutStream() {
        if (disconnected || outStream.currentOffset == 0)
            return;
        synchronized (this) {
            StaticPacketBuilder out = new StaticPacketBuilder().setBare(true);
            byte[] temp = new byte[outStream.currentOffset];
            System.arraycopy(outStream.buffer, 0, temp, 0, temp.length);
            out.addBytes(temp);
            session.write(out.toPacket());
            outStream.currentOffset = 0;
        }
    }

    public void sendClan(String name, String message, String clan, int rights) {
        outStream.createFrameVarSizeWord(217);
        outStream.writeString(name);
        outStream.writeString(message);
        outStream.writeString(clan);
        outStream.writeWord(rights);
        outStream.endFrameVarSize();
    }

    public static final int PACKET_SIZES[] = { 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, // 0
    0, 0, 0, 0, 8, 0, 6, 2, 2, 0, // 10
    0, 2, 0, 6, 0, 12, 0, 0, 0, 0, // 20
    0, 0, 0, 0, 0, 8, 4, 0, 0, 2, // 30
    2, 6, 0, 6, 0, -1, 0, 0, 0, 0, // 40
    0, 0, 0, 12, 0, 0, 0, 8, 8, 12, // 50
    8, 8, 0, 0, 0, 0, 0, 0, 0, 0, // 60
    6, 0, 2, 2, 8, 6, 0, -1, 0, 6, // 70
    0, 0, 0, 0, 0, 1, 4, 6, 0, 0, // 80
    0, 0, 0, 0, 0, 3, 0, 0, -1, 0, // 90
    0, 13, 0, -1, 0, 0, 0, 0, 0, 0,// 100
    0, 0, 0, 0, 0, 0, 0, 6, 0, 0, // 110
    1, 0, 6, 0, 0, 0, -1, 0, 2, 6, // 120
    0, 4, 6, 8, 0, 6, 0, 0, 0, 2, // 130
    0, 0, 0, 0, 0, 6, 0, 0, 0, 0, // 140
    0, 0, 1, 2, 0, 2, 6, 0, 0, 0, // 150
    0, 0, 0, 0, -1, -1, 0, 0, 0, 0,// 160
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
    0, 8, 0, 3, 0, 2, 0, 0, 8, 1, // 180
    0, 0, 12, 0, 0, 0, 0, 0, 0, 0, // 190
    2, 0, 0, 0, 0, 0, 0, 0, 4, 0, // 200
    4, 0, 0, 0, 7, 8, 0, 0, 10, 0, // 210
    0, 0, 0, 0, 0, 0, -1, 0, 6, 0, // 220
    1, 0, 0, 0, 6, 0, 6, 8, 1, 0, // 230
    0, 4, 0, 0, 0, 0, -1, 0, -1, 4,// 240
    0, 0, 6, 6, 0, 0, 0 // 250
    };

    public void destruct() {
        if (session == null)
            return;
        PlayerSave.saveGame(this);
        Misc.println("[DEREGISTERED]: " + playerName + "");
        HostList.getHostList().remove(session);
        disconnected = true;
        session.close();
        session = null;
        inStream = null;
        outStream = null;
        isActive = false;
        buffer = null;
        playerListSize = 0;
        for (int i = 0; i < maxPlayerListSize; i++)
            playerList[i] = null;
        absX = absY = -1;
        mapRegionX = mapRegionY = -1;
        currentX = currentY = 0;
        resetWalkingQueue();
    }

    public void sendMessage(String s) {
        synchronized (this) {
            if (getOutStream() != null) {
                outStream.createFrameVarSize(253);
                outStream.writeString(s);
                outStream.endFrameVarSize();
            }
        }
    }
    
    public void sendMessage(String s, Colors color) {
        synchronized (this) {
            if (getOutStream() != null) {
                outStream.createFrameVarSize(253);
                outStream.writeString(color.getColor() + s);
                outStream.endFrameVarSize();
            }
        }
    }

    public void setSidebarInterface(int menuId, int form) {
        synchronized (this) {
            if (getOutStream() != null) {
                outStream.createFrame(71);
                outStream.writeWord(form);
                outStream.writeByteA(menuId);
            }
        }
    }

    public void initialize() {
        synchronized (this) {
            outStream.createFrame(249);
            outStream.writeByteA(1); // 1 for members, zero for free
            outStream.writeWordBigEndianA(slot);
            for (int j = 0; j < Game.playerHandler.players.length; j++) {
                if (j == slot)
                    continue;
                if (Game.playerHandler.players[j] != null) {
                    if (Game.playerHandler.players[j].playerName.equalsIgnoreCase(playerName))
                        disconnected = true;
                }
            }
            for (int i = 0; i < 25; i++) {
                getPA().setSkillLevel(i, playerLevel[i], playerXP[i]);
                getPA().refreshSkill(i);
            }
            for (int p = 0; p < PRAYER.length; p++) { // reset prayer glows
                prayerActive[p] = false;
                getPA().sendFrame36(PRAYER_GLOW[p], 0);
            }
            // if (playerName.equalsIgnoreCase("Sanity")) {
            // getPA().sendCrashFrame();
            // }
            getPA().handleWeaponStyle();
            getPA().handleLoginText();
            accountFlagged = getPA().checkForFlags();
            // getPA().sendFrame36(43, fightMode-1);
            getPA().sendFrame36(108, 0);// resets autocast button
            getPA().sendFrame36(172, 1);
            getPA().sendFrame107(); // reset screen
            getPA().setChatOptions(0, 0, 0); // reset private messaging
            // options
            setSidebarInterface(1, 3917);
            setSidebarInterface(2, 638);
            setSidebarInterface(3, 3213);
            setSidebarInterface(4, 1644);
            setSidebarInterface(5, 5608);
            if (playerMagicBook == 0) {
                setSidebarInterface(6, 1151); // modern
            } else {
                if (playerMagicBook == 2) {
                    setSidebarInterface(6, 29999); // lunar
                } else {
                    setSidebarInterface(6, 12855); // ancient
                }
            }

            setSidebarInterface(7, 18128);
            setSidebarInterface(8, 5065);
            setSidebarInterface(9, 5715);
            setSidebarInterface(10, 2449);
            // setSidebarInterface(11, 4445); // wrench tab
            setSidebarInterface(11, 904); // wrench tab
            setSidebarInterface(12, 147); // run tab
            setSidebarInterface(13, -1);
            setSidebarInterface(0, 2423);
            sendMessage("@red@Welcome to " + Constants.SERVER_NAME);
            getPA().showOption(4, 0, "Trade With", 3);
            getPA().showOption(5, 0, "Follow", 4);
            getItems().resetItems(3214);
            getItems().sendWeapon(playerEquipment[playerWeapon], getItems().getItemName(playerEquipment[playerWeapon]));
            getItems().resetBonus();
            getItems().getBonus();
            getItems().writeBonus();
            getItems().setEquipment(playerEquipment[playerHat], 1, playerHat);
            getItems().setEquipment(playerEquipment[playerCape], 1, playerCape);
            getItems().setEquipment(playerEquipment[playerAmulet], 1, playerAmulet);
            getItems().setEquipment(playerEquipment[playerArrows], playerEquipmentN[playerArrows], playerArrows);
            getItems().setEquipment(playerEquipment[playerChest], 1, playerChest);
            getItems().setEquipment(playerEquipment[playerShield], 1, playerShield);
            getItems().setEquipment(playerEquipment[playerLegs], 1, playerLegs);
            getItems().setEquipment(playerEquipment[playerHands], 1, playerHands);
            getItems().setEquipment(playerEquipment[playerFeet], 1, playerFeet);
            getItems().setEquipment(playerEquipment[playerRing], 1, playerRing);
            getItems().setEquipment(playerEquipment[playerWeapon], playerEquipmentN[playerWeapon], playerWeapon);
            getCombat().getPlayerAnimIndex(getItems().getItemName(playerEquipment[playerWeapon]).toLowerCase());
            getPA().logIntoPM();
            getItems().addSpecialBar(playerEquipment[playerWeapon]);
            saveTimer = Constants.SAVE_TIMER;
            saveCharacter = true;
            Misc.println("[REGISTERED]: " + playerName + "");
            handler.updatePlayer(this, outStream);
            handler.updateNPC(this, outStream);
            flushOutStream();
            getPA().clearClanChat();
            getPA().resetFollow();
            if (addStarter)
                getPA().addStarter();
            if (autoRet == 1)
                getPA().sendFrame36(172, 1);
            else
                getPA().sendFrame36(172, 0);
        }
    }

    public void update() {
        synchronized (this) {
            handler.updatePlayer(this, outStream);
            handler.updateNPC(this, outStream);
            flushOutStream();
        }
    }

    public void logout() {
        synchronized (this) {
            if (System.currentTimeMillis() - logoutDelay > 10000) {
                outStream.createFrame(109);
                properLogout = true;
            } else {
                sendMessage("You must wait a few seconds from being out of combat to logout.");
            }
        }
    }

    public void updateSigns() {
        if (inWild()) {
            int modY = absY > 6400 ? absY - 6400 : absY;
            wildLevel = (((modY - 3520) / 8) + 1);
            getPA().walkableInterface(197);
            if (Constants.SINGLE_AND_MULTI_ZONES) {
                if (inMulti()) {
                    getPA().sendFrame126("@yel@Level: " + wildLevel, 199);
                } else {
                    getPA().sendFrame126("@yel@Level: " + wildLevel, 199);
                }
            } else {
                getPA().multiWay(-1);
                getPA().sendFrame126("@yel@Level: " + wildLevel, 199);
            }
            getPA().showOption(3, 0, "Attack", 1);
        } else if (inDuelArena()) {
            getPA().walkableInterface(201);
            if (duelStatus == 5) {
                getPA().showOption(3, 0, "Attack", 1);
            } else {
                getPA().showOption(3, 0, "Challenge", 1);
            }
        } else if (inBarrows()) {
            getPA().sendFrame99(2);
            getPA().sendFrame126("Kill Count: " + barrowsKillCount, 4536);
            getPA().walkableInterface(4535);
        } else if (inCwGame || inPits) {
            getPA().showOption(3, 0, "Attack", 1);
        } else if (getPA().inPitsWait()) {
            getPA().showOption(3, 0, "Null", 1);
        } else if (!inCwWait) {
            getPA().sendFrame99(0);
            getPA().walkableInterface(-1);
            getPA().showOption(3, 0, "Null", 1);
        }

        if (!hasMultiSign && inMulti()) {
            hasMultiSign = true;
            getPA().multiWay(1);
        }

        if (hasMultiSign && !inMulti()) {
            hasMultiSign = false;
            getPA().multiWay(-1);
        }
    }

    public int packetSize = 0, packetType = -1;
    public int donatorPoints = 0;

    public void process() {

        if (followId > 0) {
            getPA().followPlayer();
        } else if (followId2 > 0) {
            getPA().followNpc();
        }

        getCombat().handlePrayerDrain();

        if (System.currentTimeMillis() - singleCombatDelay > 3300) {
            underAttackBy = 0;
        }
        if (System.currentTimeMillis() - singleCombatDelay2 > 3300) {
            underAttackBy2 = 0;
        }

        if (System.currentTimeMillis() - restoreStatsDelay > 60000) {
            restoreStatsDelay = System.currentTimeMillis();

        }

        if (System.currentTimeMillis() - teleGrabDelay > 1550 && usingMagic) {
            usingMagic = false;
            if (Game.itemHandler.itemExists(teleGrabItem, teleGrabX, teleGrabY)) {
                Game.itemHandler.removeGroundItem(this, teleGrabItem, teleGrabX, teleGrabY, true);
            }
        }

        if (skullTimer > 0) {
            skullTimer--;
            if (skullTimer == 1) {
                isSkulled = false;
                attackedPlayers.clear();
                headIconPk = -1;
                skullTimer = -1;
                getPA().requestUpdates();
            }
        }

        if (isDead && respawnTimer == -6) {
            getPA().applyDead();
        }

        if (respawnTimer == 7) {
            respawnTimer = -6;
            getPA().giveLife();
        } else if (respawnTimer == 12) {
            respawnTimer--;
            startAnimation(0x900);
            poisonDamage = -1;
        }

        if (respawnTimer > -6) {
            respawnTimer--;
        }
        if (freezeTimer > -6) {
            freezeTimer--;
            if (frozenBy > 0) {
                if (Game.playerHandler.players[frozenBy] == null) {
                    freezeTimer = -1;
                    frozenBy = -1;
                } else if (!goodDistance(absX, absY, Game.playerHandler.players[frozenBy].absX, Game.playerHandler.players[frozenBy].absY, 20)) {
                    freezeTimer = -1;
                    frozenBy = -1;
                }
            }
        }

        if (hitDelay > 0) {
            hitDelay--;
        }

        if (teleTimer > 0) {
            teleTimer--;
            if (!isDead) {
                if (teleTimer == 1 && newLocation > 0) {
                    teleTimer = 0;
                    getPA().changeLocation();
                }
                if (teleTimer == 5) {
                    teleTimer--;
                    getPA().processTeleport();
                }
                if (teleTimer == 9 && teleGfx > 0) {
                    teleTimer--;
                    gfx100(teleGfx);
                }
            } else {
                teleTimer = 0;
            }
        }

        if (hitDelay == 1) {
            if (oldNpcIndex > 0) {
                getCombat().delayedHit(oldNpcIndex);
            }
            if (oldPlayerIndex > 0) {
                getCombat().playerDelayedHit(oldPlayerIndex);
            }
        }

        if (attackTimer > 0) {
            attackTimer--;
        }

        if (attackTimer == 1) {
            if (npcIndex > 0 && clickNpcType == 0) {
                getCombat().attackNpc(npcIndex);
            }
            if (playerIndex > 0) {
                getCombat().attackPlayer(playerIndex);
            }
        } else if (attackTimer <= 0 && (npcIndex > 0 || playerIndex > 0)) {
            if (npcIndex > 0) {
                attackTimer = 0;
                getCombat().attackNpc(npcIndex);
            } else if (playerIndex > 0) {
                attackTimer = 0;
                getCombat().attackPlayer(playerIndex);
            }
        }

        if (timeOutCounter > Constants.TIMEOUT) {
            disconnected = true;
        }

        timeOutCounter++;

        if (inTrade && tradeResetNeeded) {
            Player o = Game.playerHandler.players[tradeWith];
            if (o != null) {
                if (o.tradeResetNeeded) {
                    getTradeAndDuel().resetTrade();
                    o.getTradeAndDuel().resetTrade();
                }
            }
        }
    }

    public static final int maxPlayerListSize = Constants.MAX_PLAYERS;
    public Player playerList[] = new Player[maxPlayerListSize];
    public int playerListSize = 0;

    public byte playerInListBitmap[] = new byte[(Constants.MAX_PLAYERS + 7) >> 3];

    public static final int maxNPCListSize = NpcHandler.maxNPCs;
    public static final Object WALKING_TO_ACTIONS_KEY = new Object();
    public Npc npcList[] = new Npc[maxNPCListSize];
    public int npcListSize = 0;

    public byte npcInListBitmap[] = new byte[(NpcHandler.maxNPCs + 7) >> 3];

    public boolean withinDistance(Player otherPlr) {
        if (heightLevel != otherPlr.heightLevel)
            return false;
        int deltaX = otherPlr.absX - absX, deltaY = otherPlr.absY - absY;
        return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
    }

    public boolean withinDistance(Npc npc) {
        if (heightLevel != npc.heightLevel)
            return false;
        if (npc.needRespawn == true)
            return false;
        int deltaX = npc.absX - absX, deltaY = npc.absY - absY;
        return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
    }

    public int distanceToPoint(int pointX, int pointY) {
        return (int) Math.sqrt(Math.pow(absX - pointX, 2) + Math.pow(absY - pointY, 2));
    }

    public int mapRegionX, mapRegionY;
    public int currentX, currentY;

    public int playerSE = 0x328;
    public int playerSEW = 0x333;
    public int playerSER = 0x334;

    public final int walkingQueueSize = 50;
    public int walkingQueueX[] = new int[walkingQueueSize],
            walkingQueueY[] = new int[walkingQueueSize];
    public int wQueueReadPtr = 0;
    public int wQueueWritePtr = 0;
    public boolean isRunning = true;
    public int teleportToX = -1, teleportToY = -1;

    public void resetWalkingQueue() {
        wQueueReadPtr = wQueueWritePtr = 0;

        for (int i = 0; i < walkingQueueSize; i++) {
            walkingQueueX[i] = currentX;
            walkingQueueY[i] = currentY;
        }
        isMoving = false;
    }

    public void addToWalkingQueue(int x, int y) {
        // if (VirtualWorld.I(heightLevel, absX, absY, x, y, 0)) {
        int next = (wQueueWritePtr + 1) % walkingQueueSize;
        if (next == wQueueWritePtr)
            return;
        walkingQueueX[wQueueWritePtr] = x;
        walkingQueueY[wQueueWritePtr] = y;
        wQueueWritePtr = next;
        // }
    }

    public boolean goodDistance(int objectX, int objectY, int playerX, int playerY, int distance) {
        return Math.abs(objectX - playerX) <= distance && Math.abs(objectY - playerY) <= distance;
    }

    public int getNextWalkingDirection() {
        if (wQueueReadPtr == wQueueWritePtr)
            return -1;
        int dir;
        do {
            dir = Misc.direction(currentX, currentY, walkingQueueX[wQueueReadPtr], walkingQueueY[wQueueReadPtr]);
            if (dir == -1) {
                wQueueReadPtr = (wQueueReadPtr + 1) % walkingQueueSize;
            } else if ((dir & 1) != 0) {
                println_debug("Invalid waypoint in walking queue!");
                resetWalkingQueue();
                return -1;
            }
        } while ((dir == -1) && (wQueueReadPtr != wQueueWritePtr));
        if (dir == -1)
            return -1;
        dir >>= 1;
        currentX += Misc.directionDeltaX[dir];
        currentY += Misc.directionDeltaY[dir];
        absX += Misc.directionDeltaX[dir];
        absY += Misc.directionDeltaY[dir];
        return dir;
    }

    public boolean didTeleport = false;
    public boolean mapRegionDidChange = false;
    public int dir1 = -1, dir2 = -1;
    public boolean createItems = false;
    public int poimiX = 0, poimiY = 0;

    public synchronized void getNextPlayerMovement() {
        mapRegionDidChange = false;
        didTeleport = false;
        dir1 = dir2 = -1;

        if (teleportToX != -1 && teleportToY != -1) {
            mapRegionDidChange = true;
            if (mapRegionX != -1 && mapRegionY != -1) {
                int relX = teleportToX - mapRegionX * 8, relY = teleportToY - mapRegionY * 8;
                if (relX >= 2 * 8 && relX < 11 * 8 && relY >= 2 * 8 && relY < 11 * 8)
                    mapRegionDidChange = false;
            }
            if (mapRegionDidChange) {
                mapRegionX = (teleportToX >> 3) - 6;
                mapRegionY = (teleportToY >> 3) - 6;
            }
            currentX = teleportToX - 8 * mapRegionX;
            currentY = teleportToY - 8 * mapRegionY;
            absX = teleportToX;
            absY = teleportToY;
            resetWalkingQueue();

            teleportToX = teleportToY = -1;
            didTeleport = true;
        } else {
            dir1 = getNextWalkingDirection();
            if (dir1 == -1)
                return;
            if (isRunning) {
                dir2 = getNextWalkingDirection();
            }
            Player c = this;
            // c.sendMessage("Cycle Ended");
            int deltaX = 0, deltaY = 0;
            if (currentX < 2 * 8) {
                deltaX = 4 * 8;
                mapRegionX -= 4;
                mapRegionDidChange = true;
            } else if (currentX >= 11 * 8) {
                deltaX = -4 * 8;
                mapRegionX += 4;
                mapRegionDidChange = true;
            }
            if (currentY < 2 * 8) {
                deltaY = 4 * 8;
                mapRegionY -= 4;
                mapRegionDidChange = true;
            } else if (currentY >= 11 * 8) {
                deltaY = -4 * 8;
                mapRegionY += 4;
                mapRegionDidChange = true;
            }

            if (mapRegionDidChange/*
             * && VirtualWorld.I(heightLevel, currentX,
             * currentY, currentX + deltaX, currentY +
             * deltaY, 0)
             */) {
                currentX += deltaX;
                currentY += deltaY;
                for (int i = 0; i < walkingQueueSize; i++) {
                    walkingQueueX[i] += deltaX;
                    walkingQueueY[i] += deltaY;
                }
            }
            // CoordAssistant.processCoords(this);

        }
    }

    public void updateThisPlayerMovement(Stream str) {
        if (mapRegionDidChange) {
            str.createFrame(73);
            str.writeWordA(mapRegionX + 6);
            str.writeWord(mapRegionY + 6);
        }

        if (didTeleport) {
            str.createFrameVarSizeWord(81);
            str.initBitAccess();
            str.writeBits(1, 1);
            str.writeBits(2, 3);
            str.writeBits(2, heightLevel);
            str.writeBits(1, 1);
            str.writeBits(1, (updateRequired) ? 1 : 0);
            str.writeBits(7, currentY);
            str.writeBits(7, currentX);
            return;
        }

        if (dir1 == -1) {
            // don't have to update the character position, because we're
            // just standing
            str.createFrameVarSizeWord(81);
            str.initBitAccess();
            isMoving = false;
            if (updateRequired) {
                // tell client there's an update block appended at the end
                str.writeBits(1, 1);
                str.writeBits(2, 0);
            } else {
                str.writeBits(1, 0);
            }
            if (DirectionCount < 50) {
                DirectionCount++;
            }
        } else {
            DirectionCount = 0;
            str.createFrameVarSizeWord(81);
            str.initBitAccess();
            str.writeBits(1, 1);

            if (dir2 == -1) {
                isMoving = true;
                str.writeBits(2, 1);
                str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
                if (updateRequired)
                    str.writeBits(1, 1);
                else
                    str.writeBits(1, 0);
            } else {
                isMoving = true;
                str.writeBits(2, 2);
                str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
                str.writeBits(3, Misc.xlateDirectionToClient[dir2]);
                if (updateRequired)
                    str.writeBits(1, 1);
                else
                    str.writeBits(1, 0);
            }
        }
    }

    public void updatePlayerMovement(Stream str) {
        if (dir1 == -1) {
            if (updateRequired || chatTextUpdateRequired) {

                str.writeBits(1, 1);
                str.writeBits(2, 0);
            } else
                str.writeBits(1, 0);
        } else if (dir2 == -1) {

            str.writeBits(1, 1);
            str.writeBits(2, 1);
            str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
            str.writeBits(1, (updateRequired || chatTextUpdateRequired) ? 1 : 0);
        } else {

            str.writeBits(1, 1);
            str.writeBits(2, 2);
            str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
            str.writeBits(3, Misc.xlateDirectionToClient[dir2]);
            str.writeBits(1, (updateRequired || chatTextUpdateRequired) ? 1 : 0);
        }
    }

    public byte cachedPropertiesBitmap[] = new byte[(Constants.MAX_PLAYERS + 7) >> 3];

    public void addNewNPC(Npc npc, Stream str, Stream updateBlock) {
        int id = npc.slot;
        npcInListBitmap[id >> 3] |= 1 << (id & 7);
        npcList[npcListSize++] = npc;

        str.writeBits(14, id);

        int z = npc.absY - absY;
        if (z < 0)
            z += 32;
        str.writeBits(5, z);
        z = npc.absX - absX;
        if (z < 0)
            z += 32;
        str.writeBits(5, z);

        str.writeBits(1, 0);
        str.writeBits(14, npc.npcType);

        boolean savedUpdateRequired = npc.updateRequired;
        npc.updateRequired = true;
        npc.appendNPCUpdateBlock(updateBlock);
        npc.updateRequired = savedUpdateRequired;
        str.writeBits(1, 1);
    }

    public void addNewPlayer(Player plr, Stream str, Stream updateBlock) {
        int id = plr.slot;
        playerInListBitmap[id >> 3] |= 1 << (id & 7);
        playerList[playerListSize++] = plr;
        str.writeBits(11, id);
        str.writeBits(1, 1);
        boolean savedFlag = plr.appearanceUpdateRequired;
        boolean savedUpdateRequired = plr.updateRequired;
        plr.appearanceUpdateRequired = true;
        plr.updateRequired = true;
        plr.appendPlayerUpdateBlock(updateBlock);
        plr.appearanceUpdateRequired = savedFlag;
        plr.updateRequired = savedUpdateRequired;
        str.writeBits(1, 1);
        int z = plr.absY - absY;
        if (z < 0)
            z += 32;
        str.writeBits(5, z);
        z = plr.absX - absX;
        if (z < 0)
            z += 32;
        str.writeBits(5, z);
    }

    public int DirectionCount = 0;
    public boolean isDead = false;

    protected static Stream playerProps;
    static {
        playerProps = new Stream(new byte[100]);
    }

    protected void appendPlayerAppearance(Stream str) {
        playerProps.currentOffset = 0;

        playerProps.writeByte(playerAppearance[0]);

        playerProps.writeByte(headIcon);
        playerProps.writeByte(headIconPk);
        // playerProps.writeByte(headIconHints);
        // playerProps.writeByte(bountyIcon);

        if (playerEquipment[playerHat] > 1) {
            playerProps.writeWord(0x200 + playerEquipment[playerHat]);
        } else {
            playerProps.writeByte(0);
        }

        if (playerEquipment[playerCape] > 1) {
            playerProps.writeWord(0x200 + playerEquipment[playerCape]);
        } else {
            playerProps.writeByte(0);
        }

        if (playerEquipment[playerAmulet] > 1) {
            playerProps.writeWord(0x200 + playerEquipment[playerAmulet]);
        } else {
            playerProps.writeByte(0);
        }

        if (playerEquipment[playerWeapon] > 1) {
            playerProps.writeWord(0x200 + playerEquipment[playerWeapon]);
        } else {
            playerProps.writeByte(0);
        }

        if (playerEquipment[playerChest] > 1) {
            playerProps.writeWord(0x200 + playerEquipment[playerChest]);
        } else {
            playerProps.writeWord(0x100 + playerAppearance[2]);
        }

        if (playerEquipment[playerShield] > 1) {
            playerProps.writeWord(0x200 + playerEquipment[playerShield]);
        } else {
            playerProps.writeByte(0);
        }

        if (!Item.isFullBody(playerEquipment[playerChest])) {
            playerProps.writeWord(0x100 + playerAppearance[3]);
        } else {
            playerProps.writeByte(0);
        }

        if (playerEquipment[playerLegs] > 1) {
            playerProps.writeWord(0x200 + playerEquipment[playerLegs]);
        } else {
            playerProps.writeWord(0x100 + playerAppearance[5]);
        }

        if (!Item.isFullHelm(playerEquipment[playerHat]) && !Item.isFullMask(playerEquipment[playerHat])) {
            playerProps.writeWord(0x100 + playerAppearance[1]);
        } else {
            playerProps.writeByte(0);
        }

        if (playerEquipment[playerHands] > 1) {
            playerProps.writeWord(0x200 + playerEquipment[playerHands]);
        } else {
            playerProps.writeWord(0x100 + playerAppearance[4]);
        }

        if (playerEquipment[playerFeet] > 1) {
            playerProps.writeWord(0x200 + playerEquipment[playerFeet]);
        } else {
            playerProps.writeWord(0x100 + playerAppearance[6]);
        }

        if (playerAppearance[0] != 1 && !Item.isFullMask(playerEquipment[playerHat])) {
            playerProps.writeWord(0x100 + playerAppearance[7]);
        } else {
            playerProps.writeByte(0);
        }

        playerProps.writeByte(playerAppearance[8]);
        playerProps.writeByte(playerAppearance[9]);
        playerProps.writeByte(playerAppearance[10]);
        playerProps.writeByte(playerAppearance[11]);
        playerProps.writeByte(playerAppearance[12]);
        playerProps.writeWord(playerStandIndex); // standAnimIndex
        playerProps.writeWord(playerTurnIndex); // standTurnAnimIndex
        playerProps.writeWord(playerWalkIndex); // walkAnimIndex
        playerProps.writeWord(playerTurn180Index); // turn180AnimIndex
        playerProps.writeWord(playerTurn90CWIndex); // turn90CWAnimIndex
        playerProps.writeWord(playerTurn90CCWIndex); // turn90CCWAnimIndex
        playerProps.writeWord(playerRunIndex); // runAnimIndex
        playerProps.writeQWord(Misc.playerNameToInt64(playerName));
        combatLevel = calculateCombatLevel();
        playerProps.writeByte(combatLevel); // combat level
        playerProps.writeWord(0);
        str.writeByteC(playerProps.currentOffset);
        str.writeBytes(playerProps.buffer, playerProps.currentOffset, 0);
    }

    public int calculateCombatLevel() {
        int j = getLevelForXP(playerXP[playerAttack]);
        int k = getLevelForXP(playerXP[playerDefence]);
        int l = getLevelForXP(playerXP[playerStrength]);
        int i1 = getLevelForXP(playerXP[playerHitpoints]);
        int j1 = getLevelForXP(playerXP[playerPrayer]);
        int k1 = getLevelForXP(playerXP[playerRanged]);
        int l1 = getLevelForXP(playerXP[playerMagic]);
        int combatLevel = (int) (((k + i1) + Math.floor(j1 / 2)) * 0.25D) + 1;
        double d = (j + l) * 0.32500000000000001D;
        double d1 = Math.floor(k1 * 1.5D) * 0.32500000000000001D;
        double d2 = Math.floor(l1 * 1.5D) * 0.32500000000000001D;
        if (d >= d1 && d >= d2) {
            combatLevel += d;
        } else if (d1 >= d && d1 >= d2) {
            combatLevel += d1;
        } else if (d2 >= d && d2 >= d1) {
            combatLevel += d2;
        }
        return combatLevel;
    }

    public int getLevelForXP(int exp) {
        if (exp > 13034430) {
            return 99;
        } else {
            int points = 0;
            boolean output = false;

            for (int lvl = 1; lvl <= 99; ++lvl) {
                points = (int) ((double) points + Math.floor((double) lvl + 300.0D * Math.pow(2.0D, (double) lvl / 7.0D)));
                int var5 = (int) Math.floor((double) (points / 4));
                if (var5 >= exp) {
                    return lvl;
                }
            }

            return 99;
        }
    }

    private byte chatText[] = new byte[4096];
    private byte chatTextSize = 0;
    private int chatTextColor = 0;
    private int chatTextEffects = 0;

    protected void appendPlayerChatText(Stream str) {
        str.writeWordBigEndian(((getChatTextColor() & 0xFF) << 8) + (getChatTextEffects() & 0xFF));
        str.writeByte(playerRights);
        str.writeByteC(getChatTextSize());
        str.writeBytes_reverse(getChatText(), getChatTextSize(), 0);
    }

    public void appendForcedChat(Stream str) {
        str.writeString(forcedChat);
    }

    /**
     * Graphics
     */
    public void appendMask100Update(Stream str) {
        str.writeWordBigEndian(gfx);
        str.writeDWord(gfxVar2);
    }

    public boolean wearing2h() {
        Player c = this;
        String s = c.getItems().getItemName(c.playerEquipment[c.playerWeapon]);
        if (s.contains("2h"))
            return true;
        else if (s.contains("godsword"))
            return true;
        return false;
    }

    public void appendAnimationRequest(Stream str) {
        str.writeWordBigEndian((animation == -1) ? 65535 : animation);
        str.writeByteC(animationWait);
    }

    /**
     * Face Update
     */

    public void appendFaceUpdate(Stream str) {
        str.writeWordBigEndian(entity);
    }

    private void appendSetFocusDestination(Stream str) {
        str.writeWordBigEndianA(faceX);
        str.writeWordBigEndian(faceY);
    }

    /**
     * Hit Update
     */

    protected void appendHitUpdate(Stream str) {
        str.writeByte(hit.getDamage());
        str.writeByteA(hit.getType().getId());
        if (playerLevel[3] <= 0) {
            playerLevel[3] = 0;
            isDead = true;
        }
        str.writeByteC(playerLevel[3]); // Their current hp, for HP bar
        str.writeByte(getLevelForXP(playerXP[3]));
    }

    protected void appendHitUpdate2(Stream str) {
        str.writeByte(secondaryHit.getDamage());
        str.writeByteS(secondaryHit.getType().getId());
        if (playerLevel[3] <= 0) {
            playerLevel[3] = 0;
            isDead = true;
        }
        str.writeByte(playerLevel[3]); // Their current hp, for HP bar
        str.writeByteC(getLevelForXP(playerXP[3])); // Their max hp, for HP
    }

    public void appendPlayerUpdateBlock(Stream str) {
        if (!updateRequired && !chatTextUpdateRequired)
            return; // nothing required
        int updateMask = 0;
        if (graphicsUpdateRequired) {
            updateMask |= 0x100;
        }
        if (animationUpdateRequired) {
            updateMask |= 8;
        }
        if (forcedChatUpdateRequired) {
            updateMask |= 4;
        }
        if (chatTextUpdateRequired) {
            updateMask |= 0x80;
        }
        if (appearanceUpdateRequired) {
            updateMask |= 0x10;
        }
        if (faceEntityUpdateRequired) {
            updateMask |= 1;
        }
        if (facePositionUpdateRequired) {
            updateMask |= 2;
        }
        if (hitUpdateRequired) {
            updateMask |= 0x20;
        }

        if (secondaryHitUpdateRequired) {
            updateMask |= 0x200;
        }

        if (updateMask >= 0x100) {
            updateMask |= 0x40;
            str.writeByte(updateMask & 0xFF);
            str.writeByte(updateMask >> 8);
        } else {
            str.writeByte(updateMask);
        }

        // now writing the various update blocks itself - note that their
        // order crucial
        if (graphicsUpdateRequired) {
            appendMask100Update(str);
        }
        if (animationUpdateRequired) {
            appendAnimationRequest(str);
        }
        if (forcedChatUpdateRequired) {
            appendForcedChat(str);
        }
        if (chatTextUpdateRequired) {
            appendPlayerChatText(str);
        }
        if (faceEntityUpdateRequired) {
            appendFaceUpdate(str);
        }
        if (appearanceUpdateRequired) {
            appendPlayerAppearance(str);
        }
        if (facePositionUpdateRequired) {
            appendSetFocusDestination(str);
        }
        if (hitUpdateRequired) {
            appendHitUpdate(str);
        }
        if (secondaryHitUpdateRequired) {
            appendHitUpdate2(str);
        }
    }

    public void stopMovement() {
        if (teleportToX <= 0 && teleportToY <= 0) {
            teleportToX = absX;
            teleportToY = absY;
        }
        newWalkCmdSteps = 0;
        getNewWalkCmdX()[0] = getNewWalkCmdY()[0] = travelBackX[0] = travelBackY[0] = 0;
        getNextPlayerMovement();
    }

    private int newWalkCmdX[] = new int[walkingQueueSize];
    private int newWalkCmdY[] = new int[walkingQueueSize];
    public int newWalkCmdSteps = 0;
    private boolean newWalkCmdIsRunning = false;
    protected int travelBackX[] = new int[walkingQueueSize];
    protected int travelBackY[] = new int[walkingQueueSize];
    protected int numTravelBackSteps = 0;

    public void preProcessing() {
        newWalkCmdSteps = 0;
    }

    /**
     * End of Skill Constructors
     */

    public void queueMessage(Packet arg1) {
        // synchronized(queuedPackets) {
        // if (arg1.getId() != 41)
        queuedPackets.add(arg1);
        // else
        // processPacket(arg1);
        // }
    }

    public boolean processQueuedPackets() {
        Packet p = null;
        p = queuedPackets.poll();

        if (p == null) {
            return false;
        }
        inStream.currentOffset = 0;
        packetType = p.getId();
        packetSize = p.getLength();
        inStream.buffer = p.getData();
        if (packetType > 0) {
            // sendMessage("PacketType: " + packetType);
            PacketHandler.processPacket(this, packetType, packetSize);
        }
        timeOutCounter = 0;
        return true;
    }

    public boolean processPacket(Packet p) {

        if (p == null) {
            return false;
        }
        inStream.currentOffset = 0;
        packetType = p.getId();
        packetSize = p.getLength();
        inStream.buffer = p.getData();
        if (packetType > 0) {
            // sendMessage("PacketType: " + packetType);
            PacketHandler.processPacket(this, packetType, packetSize);
        }
        timeOutCounter = 0;
        return true;

    }

    public void postProcessing() {
        if (this.newWalkCmdSteps > 0) {
            int firstX = this.getNewWalkCmdX()[0];
            int firstY = this.getNewWalkCmdY()[0];
            boolean lastDir = false;
            boolean found = false;
            this.numTravelBackSteps = 0;
            int ptr = this.wQueueReadPtr;
            int dir = Misc.direction(this.currentX, this.currentY, firstX, firstY);
            if (dir != -1 && (dir & 1) != 0) {
                do {
                    int var13 = dir;
                    --ptr;
                    if (ptr < 0) {
                        ptr = 49;
                    }

                    this.travelBackX[this.numTravelBackSteps] = this.walkingQueueX[ptr];
                    this.travelBackY[this.numTravelBackSteps++] = this.walkingQueueY[ptr];
                    dir = Misc.direction(this.walkingQueueX[ptr], this.walkingQueueY[ptr], firstX, firstY);
                    if (var13 != dir) {
                        found = true;
                        break;
                    }
                } while (ptr != this.wQueueWritePtr);
            } else {
                found = true;
            }

            if (found) {
                this.wQueueWritePtr = this.wQueueReadPtr;
                this.addToWalkingQueue(this.currentX, this.currentY);
                int i;
                if (dir != -1 && (dir & 1) != 0) {
                    for (i = 0; i < this.numTravelBackSteps - 1; ++i) {
                        this.addToWalkingQueue(this.travelBackX[i], this.travelBackY[i]);
                    }

                    i = this.travelBackX[this.numTravelBackSteps - 1];
                    int wayPointY2 = this.travelBackY[this.numTravelBackSteps - 1];
                    int wayPointX1;
                    int wayPointY1;
                    if (this.numTravelBackSteps == 1) {
                        wayPointX1 = this.currentX;
                        wayPointY1 = this.currentY;
                    } else {
                        wayPointX1 = this.travelBackX[this.numTravelBackSteps - 2];
                        wayPointY1 = this.travelBackY[this.numTravelBackSteps - 2];
                    }

                    dir = Misc.direction(wayPointX1, wayPointY1, i, wayPointY2);
                    if (dir != -1 && (dir & 1) == 0) {
                        dir >>= 1;
                        found = false;
                        int x = wayPointX1;
                        int y = wayPointY1;

                        while (x != i || y != wayPointY2) {
                            x += Misc.directionDeltaX[dir];
                            y += Misc.directionDeltaY[dir];
                            if ((Misc.direction(x, y, firstX, firstY) & 1) == 0) {
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            this.println_debug("Fatal: Internal error: unable to determine connection vertex!  wp1=(" + wayPointX1 + ", " + wayPointY1 + "), wp2=(" + i + ", " + wayPointY2 + "), " + "first=(" + firstX + ", " + firstY + ")");
                        } else {
                            this.addToWalkingQueue(wayPointX1, wayPointY1);
                        }
                    } else {
                        this.println_debug("Fatal: The walking queue is corrupt! wp1=(" + wayPointX1 + ", " + wayPointY1 + "), " + "wp2=(" + i + ", " + wayPointY2 + ")");
                    }
                } else {
                    for (i = 0; i < this.numTravelBackSteps; ++i) {
                        this.addToWalkingQueue(this.travelBackX[i], this.travelBackY[i]);
                    }
                }

                for (i = 0; i < this.newWalkCmdSteps; ++i) {
                    this.addToWalkingQueue(this.getNewWalkCmdX()[i], this.getNewWalkCmdY()[i]);
                }
            }

            this.isRunning = this.isNewWalkCmdIsRunning() || this.isRunning2;
        }
    }

    public int getMapRegionX() {
        return mapRegionX;
    }

    public int getMapRegionY() {
        return mapRegionY;
    }

    public int getX() {
        return absX;
    }

    public int getY() {
        return absY;
    }

    public int getId() {
        return slot;
    }

    public boolean inPcBoat() {
        return absX >= 2660 && absX <= 2663 && absY >= 2638 && absY <= 2643;
    }

    public boolean inPcGame() {
        return absX >= 2624 && absX <= 2690 && absY >= 2550 && absY <= 2619;
    }

    public void setChatTextEffects(int chatTextEffects) {
        this.chatTextEffects = chatTextEffects;
    }

    public int getChatTextEffects() {
        return chatTextEffects;
    }

    public void setChatTextSize(byte chatTextSize) {
        this.chatTextSize = chatTextSize;
    }

    public byte getChatTextSize() {
        return chatTextSize;
    }

    public void setChatText(byte chatText[]) {
        this.chatText = chatText;
    }

    public byte[] getChatText() {
        return chatText;
    }

    public void setChatTextColor(int chatTextColor) {
        this.chatTextColor = chatTextColor;
    }

    public int getChatTextColor() {
        return chatTextColor;
    }

    public void setNewWalkCmdX(int newWalkCmdX[]) {
        this.newWalkCmdX = newWalkCmdX;
    }

    public int[] getNewWalkCmdX() {
        return newWalkCmdX;
    }

    public void setNewWalkCmdY(int newWalkCmdY[]) {
        this.newWalkCmdY = newWalkCmdY;
    }

    public int[] getNewWalkCmdY() {
        return newWalkCmdY;
    }

    public void setNewWalkCmdIsRunning(boolean newWalkCmdIsRunning) {
        this.newWalkCmdIsRunning = newWalkCmdIsRunning;
    }

    public boolean isNewWalkCmdIsRunning() {
        return newWalkCmdIsRunning;
    }

    private ISAACCipher inStreamDecryption = null, outStreamDecryption = null;

    public void setInStreamDecryption(ISAACCipher inStreamDecryption) {
        this.inStreamDecryption = inStreamDecryption;
    }

    public void setOutStreamDecryption(ISAACCipher outStreamDecryption) {
        this.outStreamDecryption = outStreamDecryption;
    }

    public void setCurrentTask(Future<?> task) {
        currentTask = task;
    }

    public Future<?> getCurrentTask() {
        return currentTask;
    }

    public synchronized Stream getInStream() {
        return inStream;
    }

    public synchronized int getPacketType() {
        return packetType;
    }

    public synchronized int getPacketSize() {
        return packetSize;
    }

    public synchronized Stream getOutStream() {
        return outStream;
    }

    public ItemAssistant getItems() {
        return itemAssistant;
    }

    public PlayerAssistant getPA() {
        return playerAssistant;
    }

    public DialogueHandler getDH() {
        return dialogueHandler;
    }

    public ShopAssistant getShops() {
        return shopAssistant;
    }

    public TradeAndDuel getTradeAndDuel() {
        return tradeAndDuel;
    }

    public CombatAssistant getCombat() {
        return combatAssistant;
    }

    public ActionHandler getActions() {
        return actionHandler;
    }

    public IoSession getSession() {
        return session;
    }

    public Potions getPotions() {
        return potions;
    }

    public PotionMixing getPotMixing() {
        return potionMixing;
    }

    public Food getFood() {
        return food;
    }

    public boolean samePlayer() {
        for (int j = 0; j < Game.playerHandler.players.length; j++) {
            if (j == slot)
                continue;
            if (Game.playerHandler.players[j] != null) {
                if (Game.playerHandler.players[j].playerName.equalsIgnoreCase(playerName)) {
                    disconnected = true;
                    return true;
                }
            }
        }
        return false;
    }

    public void putInCombat(int attacker) {
        underAttackBy = attacker;
        logoutDelay = System.currentTimeMillis();
        singleCombatDelay = System.currentTimeMillis();
    }

    public int[] damageTaken = new int[Constants.MAX_PLAYERS];
}