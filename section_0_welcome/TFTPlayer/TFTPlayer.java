public class TFTPlayer {
    private int gold;
    private int experience;
    private int level;

    public TFTPlayer() {
        this.gold = 0;
        this.experience = 0;
        this.level = 1;
    }

    public int getGold() {
        return this.gold;
    }

    public int getXP() {
        return this.experience;
    }

    public int getLevel() {
        return this.level;
    }

    public boolean buyXP() {
        this.gold -= 4;
        this.experience += 4;
        if (this.experience == 20) {
            this.level++;
            this.experience = 0;
        }
        return this.gold > 0;
    }

    public int gainGold() {
        this.gold += 1;
        
        // Trick to find the 10s digit of a number. 
        // If gold = 15, then gold / 10 is 1.
        int interest = this.gold / 10;
        this.gold += interest;

        return this.gold;
    }
    
}
