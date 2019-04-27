package com.unibs.zanotti.inforinvestigador.utils;

import com.unibs.zanotti.inforinvestigador.data.source.IPaperService;
import com.unibs.zanotti.inforinvestigador.data.source.IUserService;
import com.unibs.zanotti.inforinvestigador.data.source.local.PaperLocalService;
import com.unibs.zanotti.inforinvestigador.data.source.local.UserLocalService;
import com.unibs.zanotti.inforinvestigador.data.source.local.dao.impl.DummyPaperLocalDao;
import com.unibs.zanotti.inforinvestigador.data.source.local.dao.impl.DummyUserLocalDao;

/**
 * Enables injection of mock implementations at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    public static IPaperService providePaperService() {
        return PaperLocalService.getInstance(new DummyPaperLocalDao());
    }

    public static IUserService provideUserService() {
        return UserLocalService.getInstance(new DummyUserLocalDao());
    }
}
