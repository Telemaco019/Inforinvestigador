package com.unibs.zanotti.inforinvestigador.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unibs.zanotti.inforinvestigador.data.local.PaperLocalRepository;
import com.unibs.zanotti.inforinvestigador.data.local.dao.impl.DummyPaperLocalDao;
import com.unibs.zanotti.inforinvestigador.data.remote.UserFirebaseRepository;
import com.unibs.zanotti.inforinvestigador.domain.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.domain.IUserRepository;

/**
 * Enables injection of mock implementations at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    public static IPaperRepository providePaperRepository() {
        return PaperLocalRepository.getInstance(new DummyPaperLocalDao());
    }

    public static IUserRepository provideUserRepository() {
        return UserFirebaseRepository.getInstance(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance());
    }
}
