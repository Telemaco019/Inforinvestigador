package com.unibs.zanotti.inforinvestigador.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unibs.zanotti.inforinvestigador.data.local.PaperLocalService;
import com.unibs.zanotti.inforinvestigador.data.local.dao.impl.DummyPaperLocalDao;
import com.unibs.zanotti.inforinvestigador.data.remote.UserFirebaseService;
import com.unibs.zanotti.inforinvestigador.domain.IPaperService;
import com.unibs.zanotti.inforinvestigador.domain.IUserService;

/**
 * Enables injection of mock implementations at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    public static IPaperService providePaperService() {
        return PaperLocalService.getInstance(new DummyPaperLocalDao());
    }

    public static IUserService provideUserService() {
        return UserFirebaseService.getInstance(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance());
    }
}
