package helpz;

public class Constants {
    public static class Direction {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class Action{
        public static final int IDLE = 0;
        public static final int ATTACK = 1;
    }

    public static class Tiles {
        public static final int WATER_TILE = 0;
        public static final int GRASS_TILE = 1;
        public static final int ROAD_TILE = 2;
    }

    public static class Projectile {
        public static final int KUNAI = 0;
        public static final int CHAINS = 1;
        public static final int FIRE = 2;
        public static final int SLASH = 3;

        public static float getSpeed(int type) {
            return switch (type) {
                case KUNAI -> 8f;
                case CHAINS -> 4f;
                case FIRE -> 6f;
                case SLASH ->2f;
                default -> 0f;
            };
        }
    }

    public static class Turrets {
        public static final int DARK_NINJA = 0;
        public static final int YELLOW_NINJA = 1;
        public static final int RED_NINJA = 2;
        public static final int FLAMIE = 3;
        public static final int KNIGHT = 4;
        public static final int SAMURAI = 5;
        public static final int ORANGE_SORCERER = 6;
        public static final int RED_SORCERER = 7;

        public static String getName(int towerType) {
            return switch (towerType) {
                case DARK_NINJA -> "Dark Ninja";
                case YELLOW_NINJA -> "Yellow Ninja";
                case RED_NINJA -> "Red Ninja";
                case FLAMIE -> "Flame Wizard";
                case KNIGHT -> "Knight";
                case SAMURAI -> "Samurai";
                case ORANGE_SORCERER -> "Orange Sorcerer";
                case RED_SORCERER -> "Red Sorcerer";
                default -> "";
            };
        }

        public static int getStartDmg(int towerType) {
            return switch (towerType) {
                case DARK_NINJA -> 10;
                case YELLOW_NINJA -> 8;
                case RED_NINJA -> 15;
                case FLAMIE -> 12;
                case KNIGHT -> 20;
                case SAMURAI -> 30;
                case ORANGE_SORCERER -> 1;
                case RED_SORCERER -> 5;
                default -> 0;
            };
        }

        public static float getDefaultRange(int towerType) {
            return switch (towerType) {
                case DARK_NINJA -> 100;
                case YELLOW_NINJA -> 120;
                case RED_NINJA -> 150;
                case FLAMIE -> 90;
                case KNIGHT -> 50;
                case SAMURAI -> 60;
                case ORANGE_SORCERER -> 100;
                case RED_SORCERER -> 120;
                default -> 0;
            };
        }

        public static float getDefaultCoolDown(int towerType) {
            return switch (towerType) {
                case DARK_NINJA -> 1.0f;
                case YELLOW_NINJA -> 0.8f;
                case RED_NINJA -> 0.6f;
                case FLAMIE -> 1.5f;
                case KNIGHT -> 1.5f;
                case SAMURAI -> 2.5f;
                case ORANGE_SORCERER -> 3.0f;
                case RED_SORCERER -> 2.0f;
                default -> 0;
            };
        }

        public static String getSpriteName(int towerType) {
            return switch (towerType) {
                case DARK_NINJA -> "dark_ninja";
                case YELLOW_NINJA -> "yellow_ninja";
                case RED_NINJA -> "red_ninja";
                case FLAMIE -> "flame";
                case KNIGHT -> "knight";
                case SAMURAI -> "samurai";
                case ORANGE_SORCERER -> "orange_soccerrer";
                case RED_SORCERER -> "red_soccerrer";
                default -> "";
            };
        }
    }



    public static class Monsters {
        public static final int GREEN_SLIME = 0;
        public static final int BIG_GREEN_SLIME = 1;
        public static final int DARK_GREEN_SLIME = 2;
        public static final int LAVA_SLIME = 3;
        public static final int PINK_SLIME = 4;
        public static final int TOXIC_SLIME = 5;
        public static final int KING_SLIME = 6;
        public static final int MUSHROOM_MAN = 7;
        public static final int GOBLIN = 8;
        public static final int DEMON_GREEN = 9;
        public static final int ROBOT_GREEN = 10;
        public static final int SKELETON_P = 11;
        public static final int SPIRIT = 12;
        public static final int TENGU = 13;

        public static float getSpeed(int enemyType) {
            return switch (enemyType) {
                case GREEN_SLIME -> 0.5f;
                case BIG_GREEN_SLIME -> 0.8f;
                case DARK_GREEN_SLIME -> 1.1f;
                case LAVA_SLIME -> 1.2f;
                case PINK_SLIME -> 1.0f;
                case TOXIC_SLIME -> 1.1f;
                case KING_SLIME -> 1.1f;
                case MUSHROOM_MAN -> 1.2f;
                case GOBLIN -> 1.3f;
                case DEMON_GREEN -> 1.1f;
                case ROBOT_GREEN -> 0.7f;
                case SKELETON_P -> 1.2f;
                case SPIRIT -> 1.0f;
                case TENGU -> 1.0f;
                default -> 0;
            };
        }

        public static int getStartHealth(int enemyType) {
            return switch (enemyType) {
                case GREEN_SLIME -> 100;
                case BIG_GREEN_SLIME -> 150;
                case DARK_GREEN_SLIME -> 120;
                case LAVA_SLIME -> 120;
                case PINK_SLIME -> 100;
                case TOXIC_SLIME -> 120;
                case KING_SLIME -> 200;
                case MUSHROOM_MAN -> 80;
                case GOBLIN -> 160;
                case DEMON_GREEN -> 100;
                case ROBOT_GREEN -> 200;
                case SKELETON_P -> 70;
                case SPIRIT -> 50;
                case TENGU -> 180;
                default -> 0;
            };
        }

        public static int getSpriteType(int enemyType) {
            return switch (enemyType) {
                case GREEN_SLIME -> 1;
                case BIG_GREEN_SLIME -> 1;
                case DARK_GREEN_SLIME -> 1;
                case LAVA_SLIME -> 1;
                case PINK_SLIME -> 1;
                case TOXIC_SLIME -> 1;
                case KING_SLIME -> 1;
                case MUSHROOM_MAN -> 1;
                case GOBLIN -> 1;
                case DEMON_GREEN -> 2;
                case ROBOT_GREEN -> 2;
                case SKELETON_P -> 2;
                case SPIRIT -> 2;
                case TENGU -> 2;
                default -> 0;
            };
        }

        public static String getName(int enemyType) {
            return switch (enemyType) {
                case GREEN_SLIME -> "Slime";
                case BIG_GREEN_SLIME -> "Big Slime";
                case DARK_GREEN_SLIME -> "Dark Slime";
                case LAVA_SLIME -> "Lava Slime";
                case PINK_SLIME -> "Pink Slime";
                case TOXIC_SLIME -> "Toxic Slime";
                case KING_SLIME -> "King Slime";
                case MUSHROOM_MAN -> "Mushroom";
                case GOBLIN -> "Goblin";
                case DEMON_GREEN -> "Demon";
                case ROBOT_GREEN -> "Robot";
                case SKELETON_P -> "Skeleton";
                case SPIRIT -> "Spirit";
                case TENGU -> "Tengu";
                default -> "";
            };
        }

        public static String getSpriteName(int enemyType) {
            return switch (enemyType) {
                case GREEN_SLIME -> "green_slime";
                case BIG_GREEN_SLIME -> "big_green_slime";
                case DARK_GREEN_SLIME -> "dark_green_slime";
                case LAVA_SLIME -> "lava_slime";
                case PINK_SLIME -> "pink_slime";
                case TOXIC_SLIME -> "toxic_slime";
                case KING_SLIME -> "king_slime";
                case MUSHROOM_MAN -> "mushroom_man";
                case GOBLIN -> "goblin";
                case DEMON_GREEN -> "demon_green";
                case ROBOT_GREEN -> "robot_green";
                case SKELETON_P -> "skeleton";
                case SPIRIT -> "spirit";
                case TENGU -> "tengu";
                default -> "";
            };
        }
    }


}
