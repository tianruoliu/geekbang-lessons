package org.geektimes.projects.user.arithmetic;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 简单的，不支持虚拟节点的 一致性哈希算法
 *
 * @author ajin
 */
public class SimpleConsistentHashArithmetic {

    /**
     * 待加入哈希环的服务器节点列表
     */
    private static String[] servers = new String[] {"192.168.1.104:8080", "192.168.1.105:8082", "192.168.1.106:9090"};

    /**
     * 服务器hash值-> 服务器
     */
    private static SortedMap<Integer, String> sortedMap = new TreeMap<>();

    // 初始化：将所有的服务器放入 sortedMap中
    static {
        for (int i = 0; i < servers.length; i++) {
            int hash = getHash(servers[i]);
            sortedMap.put(hash, servers[i]);
        }
    }

    /**
     * 获得 应当路由到的服务器节点
     *
     * @param key 客户端 key
     */
    private static String getServer(String key) {
        // 获取key的hash值
        int hash = getHash(key);
        //得到大于该Hash值的所有Map
        SortedMap<Integer, String> moreMap = sortedMap.tailMap(hash);

        if (moreMap.isEmpty()) {
            //如果没有比该key的hash值大的，则从第一个node开始
            Integer i = sortedMap.firstKey();
            return sortedMap.get(i);
        }
        // //第一个Key就是顺时针过去离node最近的那个结点
        return sortedMap.get(moreMap.firstKey());
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
        String[] keys = {"ajin", "mercy", "zhonghuashishan"};
        for (int i = 0; i < keys.length; i++)
            // [ajin]的hash值为20655721, 被路由到结点[192.168.1.104:8080]
            // [mercy]的hash值为1209425796, 被路由到结点[192.168.1.106:9090]
            // [zhonghuashishan1]的hash值为990943046, 被路由到结点[192.168.1.105:8082]
            System.out.println("[" + keys[i] + "]的hash值为" + getHash(keys[i]) + ", 被路由到结点[" + getServer(keys[i]) + "]");
    }

}
