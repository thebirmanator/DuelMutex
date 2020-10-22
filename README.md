# DuelMutex
A simple duels plugin meant for demonstration of knowledge.

Essentially, this plugin has delays meant to simulate cross-server communication. This means that it has to be able to handle requests and "sync" them, so that a player doesn't end up in two duels at once.

## Commands
There are four commands for this plugin for sending duel requests and responding to them.

Command | Description
--- | ---
`duel` | Request a duel with a player. Must provide a username
`accept` | Accept your most recent duel request
`deny` | Deny your most recent duel request. Any requests received before this one are forgotten
`leave` | Forfeit a duel
