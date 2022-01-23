Use Item on Block Event is a forge mod that adds a cancellable forge event that fires when the player right-clicks a block.

Built jars are available here: https://www.curseforge.com/minecraft/mc-mods/use-item-on-block-event

## Dependency Setup

Forge mod projects that use official (mojang) mappings can use this mod in a dev environment with this gradle dependency:

```gradle
repositories {
	maven { url "https://cubicinterpolation.net/maven/" }
}

dependencies {
	implementation fg.deobf("commoble.useitemonblockevent:${useitemonblockevent-mcversion}:${useitemonblockevent-version}")
}
```
where
* The group id `${useitemonblockevent-mcversion}` is e.g. `useitemonblockevent-1.18.1`, indicating the version of minecraft useitemonblockevent was compiled against.
* `${useitemonblockevent-version}` is e.g. `1.0.0.0`, the build version of the mod to use.

Projects that use other mappings sets can alternatively depend on this mod via cursemaven; however, sources and javadocs will not be available. https://www.cursemaven.com/

Forge mod projects that use this mod in a dev environment must also add these arguments to all of their run config blocks:

```gradle
property 'mixin.env.remapRefMap', 'true'
property 'mixin.env.refMapRemappingFile', "${buildDir}/createSrgToMcp/output.srg"
```

## API Usage

This mod fires a `commoble.useitemonblockevent.api.UseItemOnBlockEvent`, which can be subscribed to via MinecraftForge.EVENT_BUS in the usual manner for subscribing to forge events.

This event fires on both the client and server threads when a player interacts with a block (whether holding an item or empty-handed).

The event fires in three phases:
* PRE_BLOCK -- fires just before the item's use-on-first interaction behavior is invoked
* BLOCK -- fires just before the block's interaction behavior is invoked
* POST_BLOCK -- fires just before the item's standard interaction behavior is invoked

Cancelling the event in any phase will prevent that interaction from occurring. The event also allows the InteractionResult that would normally be returned from the interaction to be set when the event is cancelled by invoking event#cancelWithResult(InteractionResult). Setting the interaction result of the event when cancelling it may also have additional effects, depending on the phase and result:

* SUCCESS, CONSUME, CONSUME_PARTIAL, and FAIL will prevent further types of interaction attempts when provided from the PRE_BLOCK phase.
* SUCCESS, CONSUME, and CONSUME_PARTIAL will trigger advancements on the server (except in the PRE_BLOCK phase), and will also prevent the POST_BLOCK item interaction from occurring if provided during the BLOCK phase.
* SUCCESS will trigger the arm-swinging mechanic.
* PASS will always allow proceeding to the next phase.

## Versioning Semantics

Use Item on Block Event's versions use the format A.B.C.D, where
* Increments to A indicate breaks in save compatibility. Worlds saved with older versions of A present may not be compatible with newer versions.
* Increments to B indicate breaking changes to APIs. Dependant mods compiled against older versions of B or A may not be compatible with newer versions.
* Increments to C indicate breaks in network compatibility. When a server updates to a newer version of C, B, or A, clients may need to update as well.
* Increments to D indicate changes to implementation details. These will generally not break anything, though depdendant mods using the internals rather than the API may need to update.

Use Item on Block Event does not currently interact with persistant data or network data, so changes to A and C are not expected to occur in the near future.
