﻿# Gradzix-Channels
Description

This plugin is designed for Minecraft servers and provides synchronization of players across multiple server instances. It consists of two modules: one for BungeeCord and one for Spigot servers. The synchronization includes player inventory, chests, health, and player locations.

Features

Player Commands:
/channel: Opens a GUI for selecting a server channel within allowed regions. Players can switch between active channels in permitted regions. Regions allowed for channel switching must have names containing allowChannelChange (e.g., allowChannelChange_1). Regions where channel switching is disallowed should have names containing disallowChannelChange (e.g., disallowChannelChange_1).
/join serverNetworkChannel [network]: Transfers the player to the least loaded server channel. This command is only accessible in the lobby.
Admin Commands:
/toggleServerChannel [channel] [on/off]: Allows manual enabling or disabling of a specific channel.


Installation

To use the plugin, you need Spigot servers with the WorldGuard and WorldEdit plugins installed. Additionally, ensure that each server where you want channels has all server names listed in the configuration file. (IMPORTANT! ALL CONFIGURATION FILES SOULD BE EXACTLY THE SAME IN ONE SERVER CHANNEL NETWORK)

Database Requirement: This plugin requires an SQL database for operation. Provide the necessary database credentials in the configuration file, and specify the names of all servers belonging to one "network."

Getting Started

1. Install the plugin on your BungeeCord and Spigot servers.
2. Configure the plugin by editing the configuration files.
3. Set up the required SQL database and provide the credentials in the configuration file.
4. Ensure WorldGuard and WorldEdit are installed on your Spigot servers.
5. Start your servers and verify that the plugin is functioning correctly.


For any questions or issues, feel free to contact me on discord: xxgradzix.
