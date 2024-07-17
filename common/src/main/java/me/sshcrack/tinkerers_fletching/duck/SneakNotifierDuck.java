package me.sshcrack.tinkerers_fletching.duck;

import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

public interface SneakNotifierDuck {
    void tinkerers$addSneakListener(SneakListener listener);

    void tinkerrers$removeSneakListener(SneakListener listener);

    void tinkerers$notifyListeners(boolean isSneaking);

    interface SneakListener {
        /**
         * Called when the entity's sneaking state changes.
         *
         * @param entity The entity that changed its sneaking state.
         * @param isSneaking The new sneaking state of the entity.
         * @return The result of the event. (CONSUME if no other listener should be called)
         */
        ActionResult onSneakChange(Entity entity, boolean isSneaking);
    }
}
