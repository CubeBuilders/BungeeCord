package io.siggi.interceptbrigadier;

import com.mojang.brigadier.tree.RootCommandNode;
import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class InterceptBrigadier {
    private InterceptBrigadier() {
    }

    private static final List<Interceptor> interceptors = new ArrayList<>();

    public static void intercept(ProxiedPlayer player, RootCommandNode<?> node, int protocolVersion) {
        for (Interceptor interceptor : interceptors) {
            try {
                interceptor.intercept(player, node, protocolVersion);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void addInterceptor(Interceptor interceptor) {
        if (interceptor == null) return;
        interceptors.add(interceptor);
    }

    @FunctionalInterface
    public interface Interceptor {
        public void intercept(ProxiedPlayer player, RootCommandNode<?> node, int protocolVersion);
    }
}
