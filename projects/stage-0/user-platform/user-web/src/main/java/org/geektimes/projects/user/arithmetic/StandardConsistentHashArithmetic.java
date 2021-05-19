package org.geektimes.projects.user.arithmetic;

import java.util.*;

/**
 * 加入了虚拟节点的一致性哈希
 *
 * @author ajin
 */

public class StandardConsistentHashArithmetic {

    private static String[] servers = new String[] {"192.168.1.104:8080", "192.168.1.105:8082", "192.168.1.106:9090"};

    /**
     * 真实结点列表,考虑到服务器上线、下线的场景，即添加、删除的场景会比较频繁，
     * 这里使用LinkedList会更好
     */
    private static List<String> realNodes = new LinkedList<>();

    /**
     * 虚拟节点，key表示虚拟节点的hash值，value表示虚拟节点的名称
     */
    private static SortedMap<Integer, String> virtualNodes = new TreeMap<>();

    /**
     * 虚拟节点的数量为10（固定）
     */
    private static final int VIRTUAL_NODE_COUNT = 10;

    static {
        realNodes.addAll(Arrays.asList(servers));

        for (String server : realNodes) {
            for (int i = 0; i < VIRTUAL_NODE_COUNT; i++) {

                String virtualNodeName = server + "_VN" + i;

                int hash = getHash(virtualNodeName);
                //虚拟节点[192.168.1.104:8080_VN0]被添加, hash值为357772862
                // 虚拟节点[192.168.1.104:8080_VN1]被添加, hash值为1671868386
                // 虚拟节点[192.168.1.104:8080_VN2]被添加, hash值为1947815017
                // 虚拟节点[192.168.1.104:8080_VN3]被添加, hash值为324522053
                // 虚拟节点[192.168.1.104:8080_VN4]被添加, hash值为1812872573
                // 虚拟节点[192.168.1.104:8080_VN5]被添加, hash值为408474540
                // 虚拟节点[192.168.1.104:8080_VN6]被添加, hash值为152362988
                // 虚拟节点[192.168.1.104:8080_VN7]被添加, hash值为1896750306
                // 虚拟节点[192.168.1.104:8080_VN8]被添加, hash值为799106103
                // 虚拟节点[192.168.1.104:8080_VN9]被添加, hash值为994113506
                // 虚拟节点[192.168.1.105:8082_VN0]被添加, hash值为31564637
                // 虚拟节点[192.168.1.105:8082_VN1]被添加, hash值为836861618
                // 虚拟节点[192.168.1.105:8082_VN2]被添加, hash值为803117778
                // 虚拟节点[192.168.1.105:8082_VN3]被添加, hash值为354643566
                // 虚拟节点[192.168.1.105:8082_VN4]被添加, hash值为356425315
                // 虚拟节点[192.168.1.105:8082_VN5]被添加, hash值为956010107
                // 虚拟节点[192.168.1.105:8082_VN6]被添加, hash值为126995917
                // 虚拟节点[192.168.1.105:8082_VN7]被添加, hash值为727557798
                // 虚拟节点[192.168.1.105:8082_VN8]被添加, hash值为1283439401
                // 虚拟节点[192.168.1.105:8082_VN9]被添加, hash值为95498417
                // 虚拟节点[192.168.1.106:9090_VN0]被添加, hash值为1592920826
                // 虚拟节点[192.168.1.106:9090_VN1]被添加, hash值为1778339539
                // 虚拟节点[192.168.1.106:9090_VN2]被添加, hash值为1850583151
                // 虚拟节点[192.168.1.106:9090_VN3]被添加, hash值为650300077
                // 虚拟节点[192.168.1.106:9090_VN4]被添加, hash值为850271855
                // 虚拟节点[192.168.1.106:9090_VN5]被添加, hash值为1290894081
                // 虚拟节点[192.168.1.106:9090_VN6]被添加, hash值为1474730797
                // 虚拟节点[192.168.1.106:9090_VN7]被添加, hash值为808994083
                // 虚拟节点[192.168.1.106:9090_VN8]被添加, hash值为1123144064
                // 虚拟节点[192.168.1.106:9090_VN9]被添加, hash值为1866318041
                System.out.println("虚拟节点[" + virtualNodeName + "]被添加, hash值为" + hash);
                virtualNodes.put(hash, virtualNodeName);
            }
        }

    }
    /**
     * 得到应该路由到的物理服务器节点
     * */
    private static String getServer(String key) {
        int hash = getHash(key);

        SortedMap<Integer, String> subMap = virtualNodes.tailMap(hash);

        Integer i = subMap.firstKey();

        String virtualNodeName = subMap.get(i);
        return virtualNodeName.substring(0,virtualNodeName.indexOf("_"));
    }

    /**
     * 使用FNV1_32_HASH算法计算服务器的Hash值
     */
    private static int getHash(String str) {
        final int p    = 16777619;
        int       hash = (int)2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }

    public static void main(String[] args) {
        // 客户端节点key组成的数组
        String[] nodes = {"127.0.0.1:1111", "221.226.0.1:2222", "10.211.0.1:3333"};
           for (int i = 0; i < nodes.length; i++) {
               // [127.0.0.1:1111]的hash值为380278925, 被路由到结点[192.168.1.104:8080]
               // [221.226.0.1:2222]的hash值为1493545632, 被路由到结点[192.168.1.106:9090]
               // [10.211.0.1:3333]的hash值为1393836017, 被路由到结点[192.168.1.106:9090]
               System.out.println("[" + nodes[i] + "]的hash值为" + getHash(nodes[i]) + ", 被路由到结点[" + getServer(nodes[i]) + "]");
           }
    }
}
