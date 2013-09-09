package model.npc;

import gui.UserInterface;
import gui.sprite.Posture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import model.Coord;
import model.messages.Message;
import model.messages.Question;
import model.player.Player;
import model.rooms.Room;
import controller.Direction;
import controller.actions.IMenuAction;
import controller.actions.ShowMessage;

/**
 * @author Nicolas Kniebihler
 *
 */
public class NPC extends Player {
    private static String npcDescriptorFilePath = "/characters/characters.txt";
    private static HashMap<String, NPC> npcList;

    private String name;
    private List<Direction> moveScript = new ArrayList<Direction>();
    private List<Message> speech = new ArrayList<Message>();
    private int currentStepInScript = 0;

    private Coord spriteCoord;

    public NPC(Coord coord, Posture posture, int id, String name, Room startRoom, List<Direction> moveScript, List<Message> speech, Coord spriteCoord) {
        super(id, startRoom, coord, posture);
        this.name = name;

        if (!moveScript.isEmpty()) {
            this.moveScript.addAll(moveScript);
            this.getMove().setDir(moveScript.get(0));
        }

        if (!speech.isEmpty()) {
            this.speech.addAll(speech);
        }

        this.spriteCoord = spriteCoord;
    }

    public String getName() {
        return name;
    }

    public Coord getSpriteCoord() {
        return spriteCoord;
    }

    public List<Message> getSpeech() {
        return speech;
    }

    public Direction getCurrentDirectionInScript() {
        return moveScript.isEmpty() ? Direction.NONE : moveScript.get(currentStepInScript);
    }

    public void goToNextStepOfScript() {
        ++currentStepInScript;
        if (currentStepInScript >= moveScript.size()) {
            currentStepInScript = 0;
        }

        Direction nextDir = moveScript.get(currentStepInScript);
        setMovementIFP(nextDir);
    }

    public static Collection<NPC> getNPCList() {
        return npcList.values();
    }

    public static NPC getNPC(String id) {
        return npcList.get(id);
    }

    public static NPC getNPC(int id) {
        for (NPC npc : npcList.values()) {
            if (npc.getId() == id) {
                return npc;
            }
        }

        assert false : "NPC not found : " + id;
        return null;
    }

    public static List<NPC> getNPCsInRoom(Room room) {
        List<NPC> returnList = new ArrayList<NPC>();
        for (NPC npc : npcList.values()) {
            if (npc.getRoom() == room) {
                returnList.add(npc);
            }
        }
        return returnList;
    }

    public static void createNPC() throws IOException {
        npcList = new HashMap<String, NPC>();

        URL url = NPC.class.getResource(npcDescriptorFilePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        assert reader != null;

        String line = null;
        String currentNPCId = null;
        int currentNumericId = 100;
        String currentNPCName = null;
        String currentNPCStartRoom = null;
        Coord currentNPCStartCoord = null;
        Posture currentNPCStartPosture = null;
        List<Direction> currentNPCMoveScript = new ArrayList<Direction>();
        List<Message> currentNPCSpeech = new ArrayList<Message>();
        Coord currentNPCspriteCoord = null;

        while ((line = reader.readLine()) != null) {
            // ID
            if (line.startsWith("C_")) {
                currentNPCId = line;
                ++currentNumericId;
            }
            // NAME
            else if (line.startsWith("name=")) {
                String[] tokens = line.split("=");
                currentNPCName = tokens[1];
            }
            // START ROOM
            else if (line.startsWith("startRooom=")) {
                String[] tokens = line.split("=");
                currentNPCStartRoom = tokens[1];
            }
            // START COORD
            else if (line.startsWith("startCoord=")) {
                String[] tokens = line.split("=");
                String[] coords = tokens[1].split(",");
                currentNPCStartCoord = new Coord(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
            }
            // START POSTURE
            else if (line.startsWith("startPosture=")) {
                String[] tokens = line.split("=");
                String posture = tokens[1];

                assert posture.startsWith("LOOK_") : "Start postures for characters have to be static (LOOK_...). Value found : " + posture;

                if (posture.equals("LOOK_UP")) {
                    currentNPCStartPosture = Posture.LOOK_UP;
                }
                else if (posture.equals("LOOK_RIGHT")) {
                    currentNPCStartPosture = Posture.LOOK_RIGHT;
                }
                else if (posture.equals("LOOK_DOWN")) {
                    currentNPCStartPosture = Posture.LOOK_DOWN;
                }
                else if (posture.equals("LOOK_LEFT")) {
                    currentNPCStartPosture = Posture.LOOK_LEFT;
                }
                else {
                    assert false : "Start postures for characters have to be static (LOOK_...). Value found : " + posture;
                }
            }
            // MOVE SCRIPT
            else if (line.startsWith("moveScript=")) {
                String[] tokens = line.split("=");
                String[] script = tokens[1].split(";");

                for (String move : script) {
                    Direction dir = Direction.getDirectionFromString(move);
                    currentNPCMoveScript.add(dir);
                }
            }
            // SPEECH
            else if (line.startsWith("speech=")) {
                String[] tokens = line.split("=");
                String[] speech = tokens[1].split(";");

                for (String str : speech) {
                    if (str.startsWith("?")) {
                        String subStr = str.substring(1, str.length());
                        String[] questionTokens = subStr.split("\\(");

                        String question = questionTokens[0];

                        String[] answersTokens = questionTokens[1].substring(0, questionTokens[1].length() - 1).split(",");

                        List<String> possibleAnswers = new ArrayList<String>();
                        List<IMenuAction> actions = new ArrayList<IMenuAction>();
                        for (String answer : answersTokens) {
                            String[] answerTokens = answer.split(":");
                            possibleAnswers.add(UserInterface.getLang().getString(answerTokens[0]));
                            actions.add(new ShowMessage(new Message(currentNPCName + " : " + UserInterface.getLang().getString(answerTokens[1]))));
                        }

                        Question q = new Question(currentNPCName + " : " + UserInterface.getLang().getString(question), possibleAnswers, actions);
                        currentNPCSpeech.add(q);
                    }
                    else {
                        currentNPCSpeech.add(new Message(currentNPCName + " : " + UserInterface.getLang().getString(str)));
                    }
                }
            }
            // START COORD
            else if (line.startsWith("spriteCoord=")) {
                String[] tokens = line.split("=");
                String[] coords = tokens[1].split(",");
                currentNPCspriteCoord = new Coord(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
            }
            // END LINE -> construct item
            else if (line.equals("END"))
            {
                Room startRoom = Room.getRoom(currentNPCStartRoom);

                NPC npc = new NPC(currentNPCStartCoord, currentNPCStartPosture, currentNumericId, currentNPCName, startRoom, currentNPCMoveScript, currentNPCSpeech, currentNPCspriteCoord);
                npcList.put(currentNPCId, npc);

                currentNPCId = null;
                currentNPCName = null;
                currentNPCStartRoom = null;
                currentNPCStartCoord = null;
                currentNPCStartPosture = null;
                currentNPCMoveScript.clear();
                currentNPCSpeech.clear();
            }
        }

        reader.close();
    }
}
