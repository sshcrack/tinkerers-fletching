package me.sshcrack.tinkerers_fletching.duck;

import me.sshcrack.tinkerers_fletching.entity.arrows.LeadArrowEntity;
import net.minecraft.entity.data.TrackedData;
import org.jetbrains.annotations.Nullable;

public interface PlayerEntityDuck {
    void tinkerers$setLeadArrow(@Nullable LeadArrowEntity arrow);

    @Nullable
    LeadArrowEntity tinkerers$getLeadArrow();

    void tinkerers$onTrackedDataSet(TrackedData<?> data);
}
