package managers;

import events.Wave;
import scenes.Playing;

import java.util.ArrayList;
import java.util.Arrays;

public class WaveManager {
    private Playing playing;
    private ArrayList<Wave> waves = new ArrayList<>();
    private int spawnTickLimit = 60 * 1;
    private int spawnTick = spawnTickLimit;
    private int waveIndex, enemyIndex;
    private int waveTickLimit = 5 * 60;
    private int waveTick = 0;
    private boolean waveStartTimer;
    private boolean waveTickTimerOver;

    public WaveManager(Playing playing) {
        this.playing = playing;
        createWaves();
    }

    public void update(){
        if (spawnTickLimit > spawnTick){
            spawnTick++;
        }

        if (waveStartTimer){
            waveTick++;
            if (waveTick >= waveTickLimit){
                waveTickTimerOver = true;
            }
        }
    }

    public void increaseWaveIndex(){
        waveIndex++;
        waveTickTimerOver = false;
        waveStartTimer = false;
    }

    public boolean isWaveTimerOver() {
        return waveTickTimerOver;
    }

    public void startWaveTimer() {
        waveStartTimer = true;
    }

    public int getNextEnemy(){
        spawnTick = 0;
        return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
    }

    private void createWaves() {
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(4,1,1,1,1,1,1,1,2,3))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(4,1,6,1,1,1,1, 0,2,3))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(4,1,0,1,2,1, 7,1,2,3))));
    }

    public ArrayList<Wave> getWaves() {
        return waves;
    }

    public boolean isTimeForNewEnemy() {
        return spawnTick >= spawnTickLimit;
    }

    public boolean isWaveNotEmpty(){
        return enemyIndex  < waves.get(waveIndex).getEnemyList().size();
    }

    public boolean isMoreWaves() {
        return waveIndex + 1 < waves.size();
    }

    public void resetEnemiesIndex() {
        enemyIndex = 0;
    }

    public int getWaveIndex() {
        return waveIndex;
    }

    public float getTimeLeft(){
        float ticksLeft = waveTickLimit - waveTick;
        return ticksLeft / 60.0f;
    }

    public boolean isWaveTimerStarted() {
        return waveStartTimer;
    }
}
