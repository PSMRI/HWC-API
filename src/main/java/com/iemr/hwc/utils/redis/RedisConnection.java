/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.hwc.utils.redis;

public class RedisConnection {
	// // static ConfigProperties configProperties;
	// // @Autowired(required = true)
	// // @Required
	// // public void setConfigProperties(ConfigProperties configProperties)
	// // {
	// // if (configProperties == null) {
	// // configProperties = new ConfigProperties();
	// // }
	// // this.configProperties = configProperties;
	// // }
	// static JedisPool jedisPool = null;
	// static int paasConnectionCounter = 0;
	// static int redisPort;// = new ConfigProperties().getRedisPort();
	// static String redisURL;// = new ConfigProperties().getRedisUrl();
	//
	// private void intializeRedisPool() throws NumberFormatException {
	// // if (configProperties == null) {
	// // configProperties = new ConfigProperties();
	// // }
	// redisPort = ConfigProperties.getRedisPort();
	// redisURL = ConfigProperties.getRedisUrl();
	// JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
	// jedisPoolConfig.setMaxIdle(1);
	// jedisPoolConfig.setMinIdle(1);
	// jedisPoolConfig.setMaxTotal(500);
	//
	// // jedisPool = new JedisPool(jedisPoolConfig, "localhost", 6379);
	// jedisPool = new JedisPool(jedisPoolConfig, redisURL, redisPort);
	// }
	//
	// public Jedis getRedisConnection() throws RedisSessionException {
	// Jedis jedis = null;
	// try {
	// if (jedisPool == null) {
	// intializeRedisPool();
	// }
	// jedis = jedisPool.getResource();
	// } catch (JedisConnectionException | JedisDataException e) {
	// throw new RedisSessionException("Unable to connect to Redis server", e);
	// } catch (Exception e) {
	// if ((e instanceof SocketException)) {
	// throw new RedisSessionException("Not a proper config format", e);
	// }
	// throw new RedisSessionException("Redis exception occured on fetch", e);
	// }
	// return jedis;
	// }
	//
	// public void closeRedisConnection(Jedis jedis) throws
	// JedisConnectionException, JedisDataException {
	// if ((jedisPool != null) && (jedis != null)) {
	// jedisPool.returnResource(jedis);
	// jedis = null;
	// }
	// }
}
