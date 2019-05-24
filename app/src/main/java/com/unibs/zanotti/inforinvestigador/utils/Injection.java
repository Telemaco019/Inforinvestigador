package com.unibs.zanotti.inforinvestigador.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.unibs.zanotti.inforinvestigador.data.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.data.remote.PaperFirebaseRepository;
import com.unibs.zanotti.inforinvestigador.data.remote.UserFirebaseRepository;

/**
 * Enables injection of mock implementations at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    public static IPaperRepository providePaperRepository() {
        return PaperFirebaseRepository.getInstance(FirebaseFirestore.getInstance());
    }

    public static IUserRepository provideUserRepository() {
        return UserFirebaseRepository.getInstance(FirebaseFirestore.getInstance(),
                FirebaseAuth.getInstance(),
                FirebaseStorage.getInstance());
    }
}
