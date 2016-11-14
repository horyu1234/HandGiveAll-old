/*******************************************************************************
 * Copyright (c) 2014~2016 HoryuSystems Ltd. All rights reserved.
 *
 * 본 저작물의 모든 저작권은 HoryuSystems 에 있습니다.
 *
 * 소스를 참고하여 다른 프로그램을 제작하는 것은 허용되지만,
 * 프로그램의 접두사, 기능등의 수정 및 배포는 불가능합니다.
 *
 * 기능을 거의 똑같이 하여 제작하는 행위등은 '참고하여 다른 프로그램을 제작한다는 것' 에 해당하지 않습니다.
 *
 * ============================================
 * 본 소스를 참고하여 프로그램을 제작할 시 해당 프로그램에 본 소스의 출처/라이센스를 공식적으로 안내를 해야 합니다.
 * 출처: https://github.com/horyu1234
 * 라이센스: Copyright (c) 2014~2016 HoryuSystems Ltd. All rights reserved.
 * ============================================
 *
 * 자세한 내용은 https://horyu1234.com/EULA 를 확인해주세요.
 ******************************************************************************/

package com.horyu1234.handgiveall.utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.Random;

public class RandomFireworks {
    private static RandomFireworks fireWorks = new RandomFireworks();

    public static RandomFireworks getManager() {
        return fireWorks;
    }

    private ArrayList<Color> colors = new ArrayList<Color>();
    private ArrayList<FireworkEffect.Type> types = new ArrayList<FireworkEffect.Type>();

    public void addColors() {
        colors.add(Color.WHITE);
        colors.add(Color.PURPLE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.AQUA);
        colors.add(Color.BLUE);
        colors.add(Color.FUCHSIA);
        colors.add(Color.GRAY);
        colors.add(Color.LIME);
        colors.add(Color.MAROON);
        colors.add(Color.YELLOW);
        colors.add(Color.SILVER);
        colors.add(Color.TEAL);
        colors.add(Color.ORANGE);
        colors.add(Color.OLIVE);
        colors.add(Color.NAVY);
        colors.add(Color.BLACK);
    }

    public void addTypes() {
        types.add(FireworkEffect.Type.BURST);
        types.add(FireworkEffect.Type.BALL);
        types.add(FireworkEffect.Type.BALL_LARGE);
        types.add(FireworkEffect.Type.CREEPER);
        types.add(FireworkEffect.Type.STAR);
    }

    private FireworkEffect.Type getRandomType() {
        int size = types.size();
        Random ran = new Random();
        return types.get(ran.nextInt(size));
    }

    private Color getRandomColor() {
        int size = colors.size();
        Random ran = new Random();
        return colors.get(ran.nextInt(size));
    }

    public void launchRandomFirework(Location loc) {
        Firework fw = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta fm = fw.getFireworkMeta();
        fm.setPower(1);
        fm.addEffects(FireworkEffect.builder().with(getRandomType())
                .withColor(getRandomColor()).build());
        fw.setFireworkMeta(fm);
    }
}