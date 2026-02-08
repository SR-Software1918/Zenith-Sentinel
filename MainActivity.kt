
package com.zenith.sentinel

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.zenith.sentinel.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val repo = SentinelRepository()
    private val engine = SentinelEngine()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRefresh.setOnClickListener {
            runSentinelOrchestration()
        }
        
        runSentinelOrchestration()
    }

    private fun runSentinelOrchestration() {
        if (!isNetworkActive()) {
            binding.tvDiagnostic.text = getString(R.string.err_no_internet)
            return
        }

        lifecycleScope.launch {
            try {
                setLoading(true)

                // Step 1: Spatial Sync
                binding.tvDiagnostic.text = "PHASE_1: SPATIAL_ACQUISITION"
                val ip = try { repo.getIpData() } catch(e: Exception) { null }
                
                if (ip != null) {
                    binding.tvLocation.text = "${ip.city}, ${ip.countryCode} (${ip.zip})"
                    binding.tvIsp.text = "${ip.isp} • ${ip.query}"
                    
                    // Step 2: Temporal Sync
                    binding.tvDiagnostic.text = "PHASE_2: TEMPORAL_SYNC"
                    val time = repo.getTimeData(ip.timezone)

                    // Step 3: Local Detail (Optional)
                    binding.tvDiagnostic.text = "PHASE_3: REGIONAL_MAPPING"
                    try { repo.getDetailedArea(ip.countryCode, ip.zip) } catch(e: Exception) { null }

                    // Step 4: Cognitive Logic
                    binding.tvDiagnostic.text = "PHASE_4: SENTINEL_LOGIC"
                    val mode = engine.determineMode(time)
                    val query = engine.getQueryForMode(mode)

                    // Step 5: Content Injection
                    binding.tvDiagnostic.text = "PHASE_5: CONTENT_INJECTION"
                    val finalRecommendation = if (mode == "RELAXATION_PROTOCOL") {
                        val joke = try { repo.getJoke() } catch(e: Exception) { null }
                        joke?.joke ?: getString(R.string.data_unavailable)
                    } else {
                        val knowledge = try { repo.getKnowledge(query) } catch(e: Exception) { null }
                        knowledge?.docs?.firstOrNull()?.let { 
                            "${it.title} (${it.firstPublishYear ?: "N/A"})"
                        } ?: getString(R.string.data_unavailable)
                    }

                    // Final UI Update
                    binding.tvMode.text = mode.replace("_", " ")
                    binding.tvRecommendation.text = finalRecommendation
                    binding.tvDiagnostic.text = "SYSTEM_STATUS: STABLE_SYNC_100%"
                } else {
                    binding.tvDiagnostic.text = getString(R.string.err_protocol)
                }

                setLoading(false)

            } catch (e: Exception) {
                binding.tvDiagnostic.text = "FATAL_ERROR: ${e.message}"
                setLoading(false)
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.INVISIBLE
        binding.btnRefresh.isEnabled = !loading
        binding.systemStatus.text = if (loading) getString(R.string.status_loading) else getString(R.string.status_ready)
    }

    private fun isNetworkActive(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetwork != null
    }
}
