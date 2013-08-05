BOSRewards
a bukkit plugin by ievans3024
written originally for the Brotherhood of Slaughter Minecraft Server
http://www.bhslaughter.com

This plugin provides a system for administrators and server operators
to create rewards that players may purchase with an arbitrary number of points.

Rewards points may be distributed by administrators or through console commands
executed by other plugins.

Rewards are set up to execute commands as console when a user purchases them, 
so plugins receiving commands from this plugin must accept commands sent by 
console (most plugins do.)


I. Permissions Nodes
--------------------------------------------------

  BOSRewards.*:
    description: All BOSRewards permissions
    default: false
    children:
      BOSRewards.admin.*: true
      BOSRewards.user.*: true
      BOSRewards.util.*: true
  BOSRewards.admin.*:
    description: All BOSRewards admin permissions
    default: false
    children:
      BOSRewards.admin.givepoints: true
      BOSRewards.admin.takepoints: true
      BOSRewards.admin.setpoints: true
      BOSRewards.admin.seebalance: true
      BOSRewards.admin.seehistory: true
      BOSRewards.admin.bypass: true
      BOSRewards.admin.info: true
  BOSRewards.user.*:
    description: All normal user permissions
    default: true
    children:
      BOSRewards.user.list: true
      BOSRewards.user.balance: true
      BOSRewards.user.redeem: true
      BOSRewards.user.help: true
      BOSRewards.user.history: true
  BOSRewards.util.*:
    description: All utility permissions
    default: false
    children:
      BOSRewards.util.reload: true
      BOSRewards.util.dumpsql: true
      BOSRewards.util.createreward: true
      BOSRewards.util.removereward: true
      BOSRewards.util.editreward: true


II. Configuration File
---------------------------------------------------

user-default-points
  -default value: 0
  -number of points to give to a new user when they join the server


point-name
  -default value: point
  -the word to use for one point


point-name-plural
  -default value: points
  -the word to use for multiple points

command-blocks
  -child options define whether command blocks have access to subcommands
  -all subcommands are blocked by default unless explicitly defined with this section
  -subcommand aliases are supported, but must be explicitly defined
  -options (set to true to allow)
    add
    balance
    edit
    give
    help
    history
    info
    list
    redeem
    reload
    remove
    set
    take
    bal
    get
    hist
    rm
    sub
    subtract
    
database-type
  -default value: yaml
  -currently no other value will do anything


database-host
  -unused, with planned usage for sql databases


database-name
  -unused, with planned usage for sql databases


database-username
  -unused, with planned usage for sql databases


database-password
  -unused, with planned usage for sql databases


database-table-rewards
  -unused, with planned usage for sql databases


database-table-users
  -unused, with planned usage for sql databases


III. Rewards File


Rewards properties:


<id>
the id by which the reward is referred to when accessing 
it with various BOSRewards commands


<summary>
A very short description of what the reward is or does


<cost>
The number of points that are removed from a user’s 
balance on purchase of the reward

<permission>
A permission node string

<command>
A quoted text string of a command to execute as console 
when the reward is purchased. Multiple of these can be 
supplied. These should be typed as they would be when 
typing from the server console.


Format:
<id>:
  summary: <summary>
  cost: <cost>
  permissions: 
    - <permission1>
    - <permission2>
    - etc.
  commands:
    - <command1>
    - <command2>
    - etc.


Example:
examplereward:
  summary: A Compliment
  cost: 1
  permissions: 
    - example.reward
  commands:
    - "say ${user} bought a compliment!"
    - "say ${user} is awesome!"
    - "give ${user} 38 1”


Note that commands are not preceded by a forward slash (“/”)
        
The ${user} keyword will be replaced with the username 
of the user who is purchasing the reward when they purchase 
it. if necessary, you may escape this behavior like so: \$\{user\}
\$\{user\} will then be interpreted as literally ${user} instead 
of the username. you would only need this if another plugin's command 
uses ${user} for something else.

The permission section, if supplied, determines if the reward can be seen in
/rewards list, and if the reward can be purchased with points. If no permission
nodes are specified, the reward is automatically visible/purchasable for everyone.

If the cost of the reward is a negative number, the reward is disabled
and requires the BOSRewards.admin.bypass permission to see/purchase. If
a player has this permission, rewards that do not have a negative cost will not
take points from them on purchase, and their transaction history will not
be recorded. This is useful for testing rewards.



IV. Commands
-----------------------------------------------------------------

/rewards (alias: /rw)
  - the root command, does nothing by itself.

/rewards add
  -adds a reward that can be bought to the rewards catalogue.
  -permission node: BOSRewards.util.createreward
  -usage: /rewards add <id> <summary>

/rewards balance (alias: /rewards bal)
  -tells the number of rewards points a user has collected
  -permission node: BOSRewards.user.balance
  -usage: /rewards balance [<user>]
  -optional <user> argument will return the points balance of the provided user, if they have any. 
  -supplying <user> requires that the sender has BOSRewards.admin.seebalance permission node.

/rewards edit 
  -edits the properties of an existing reward
  -permission node: BOSRewards.util.editreward
  -usage: /rewards edit <id> <flag> <value>
    -<id> is the reward id that was specified at creation
    -<value> is the value to set the property relating to <flag>
    -<flag> is one of several flags:
    
      cost
		-requires a number
		-negative numbers disable the reward for normal users
	  
      commands (alias cmds)
		-sets the commands to exactly one command
	  
      +commands (alias +cmds)
		-adds another command to the reward commands list
	  
      -commands (alias -cmds)
		-requires a number
		-removes the command at the index supplied
		-use /rewards info <id> to find the index of a command (number on the left of the command)
	  
      summary
	  	-sets the summary of the reward
	  	
	  permission (alias perm)
	  	-sets the permission nodes
	  	-multiple nodes may be supplied, separated by spaces
	  
	  +permission (alias +perm)
	  	-adds a permission node
	  	-only one may be supplied
	  
	  -permission (alias -perm)
	  	-requires a number
	  	-removes the node at the index supplied
	  	-use /rewards info <id> to find the index of a node (number on the left of the node)
    
/rewards give 
  -gives a user points
  -permission node: BOSRewards.admin.givepoints
  -usage: /rewards give <user> <points>

/rewards help 
  -prints help messages for all subcommands.
  -permission node: BOSRewards.user.help
  -usage: /rewards help [<subcommand>|<page>]
    -<subcommand> is optional
    -<page> is optional
    -<subcommand> or <page> should be suppied, not both.

/rewards history (alias /rewards hist) 
  -prints a paged list of rewards purchase history for a particular user
  -permission node: BOSRewards.user.history
  -usage: /rewards history [<user>|<page>] [<page>]
    -<user> is optional, specifies a user to get the purchase history for.
    -<page> is optional, specifies a section of history to display.
    -supplying <user> requires permission node BOSRewards.admin.seehistory 

/rewards info
  -prints information about a reward
  -permission node: BOSRewards.admin.info
  -usage: /rewards info <id>

/rewards list
  -lists all rewards the user can purchase from the catalogue
  -permission node: BOSRewards.user.list
  -usage: /rewards list [<page>]
    -<page> is optional, specifies a section of the catalogue to display
    
/rewards redeem (alias: /rewards get)
  -purchases a reward by its id
  -permission node: BOSRewards.user.redeem
  -usage: /rewards redeem <id>
  -note: a user with BOSRewards.admin.bypass permission will:
    -not be limited to rewards with 0 or positive costs
    -not require points to buy rewards
    -not have their reward purchase logged in their reward history.
    
/rewards remove (alias /rewards rm)
  -removes one or more rewards from the rewards database
  -permission node: BOSRewards.util.remove
  -usage: /rewards remove <id> [<id> [<id>]...]
    -at least one id must be specified
    -optionally separate multiple reward ids with spaces
    
/rewards set 
  -sets a user’s points balance to a specific value
  -permission node: BOSRewards.admin.setpoints
  -usage: /rewards set <user> <points>

/rewards take (aliases: subtract, sub) 
  -removes points from a user’s points balance
  -permission node: BOSRewards.admin.takepoints
  -usage: /rewards take <user> <points>