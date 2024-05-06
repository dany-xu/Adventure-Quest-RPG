package event;
import monster.Monster;
import role.Role;

public class Fight {

    private Monster monster;
    private Role role;

    public Fight(Monster monster, Role role) {
        this.monster = monster;
        this.role = role;
    }

    /**
     * Starts the combat between the monster and the role and returns a string detailing the combat.
     * @return String containing details of the entire combat sequence.
     */
    public String startCombat() {
        StringBuilder combatLog = new StringBuilder(); // StringBuilder to store combat information

        while (monster.getHP() > 0 && role.getHP() > 0) {
            // Role attacks first
            if (!monster.Dodge()) {
                double damageDealtToMonster = monster.defense(role.attack());
                monster.setHP(monster.getHP() - damageDealtToMonster);
                combatLog.append(role.getName()).append(" attacks and deals ")
                        .append(String.format("%.2f", damageDealtToMonster)).append(" damage to ")
                        .append(monster.getName()).append("\n");
            } else {
                combatLog.append(monster.getName()).append(" dodges the attack from ")
                        .append(role.getName()).append("\n");
            }

            // Check if the monster is defeated
            if (monster.getHP() <= 0) {
                combatLog.append(monster.getName()).append(" has been defeated!\n");
                break;
            }

            // Monster attacks back
            if (!role.Dodge()) {
                double damageDealtToRole = role.defense(monster.attack());
                role.setHP(role.getHP() - damageDealtToRole);
                combatLog.append(monster.getName()).append(" attacks and deals ")
                        .append(String.format("%.2f", damageDealtToRole)).append(" damage to ")
                        .append(role.getName()).append("\n");
            } else {
                combatLog.append(role.getName()).append(" dodges the attack from ")
                        .append(monster.getName()).append("\n");
            }

            // Check if the role is defeated
            if (role.getHP() <= 0) {
                combatLog.append(role.getName()).append(" has been defeated!\n");
                break;
            }
        }

        return combatLog.toString();
    }

    // Getters for testing or other purposes
    public Monster getMonster() {
        return this.monster;
    }

    public Role getRole() {
        return this.role;
    }
}
